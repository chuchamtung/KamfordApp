package com.kamford.app.compose.web.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.MainRepository
import com.kamford.app.data.local.articles.Article
import com.kamford.app.data.local.articles.ArticlesStore
import com.kamford.app.data.local.articles.asStoreArticleList
import com.kamford.app.data.local.products.Product
import com.kamford.app.data.local.products.ProductsStore
import com.kamford.app.data.local.products.asStoreProductList
import com.kamford.app.di.RoomModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WebHomeViewModel (
    private val articleStore: ArticlesStore = RoomModule.articleStore,
    private val productStore: ProductsStore = RoomModule.productStore
) : ViewModel() {
    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()

    private val _state = MutableStateFlow(WebHomeViewState())
    val state: StateFlow<WebHomeViewState>
        get() = _state

    init {
        viewModelScope.launch {
            var articleListFlow = articleStore.articleListByCourseId(author = "kamford_web",courseId = "ce215649-86f3-4d1e-917b-8547c0bdef6b",limit = 12)
            if (articleListFlow.first().isEmpty()){
                refresh(true)
            }else{
                _state.update { it.copy(
                    highlightedArticle = articleListFlow.first().get(0),
                    recommendedArticles = articleListFlow.first().subList(1,4),
                    recentArticles = articleListFlow.first().subList(5,11)
                ) }
            }
            var productListFlow = productStore.productListByOrder(limit = 6)

            _state.update { it.copy(
                popularProducts = productListFlow.first()
            ) }
        }

    }

    private fun refresh(force: Boolean) {
        if (force){
            viewModelScope.launch {
                try {
                    val storeArticleList = repository.getWebCourseItem("ce215649-86f3-4d1e-917b-8547c0bdef6b",-1,0,0,12).data.asStoreArticleList()
                    articleStore.addArticleList(storeArticleList)

                    _state.update { it.copy(
                        highlightedArticle = storeArticleList?.toList()?.get(0),
                        recommendedArticles = storeArticleList?.toList()?.subList(1,4),
                        recentArticles = storeArticleList?.toList()?.subList(5,11)
                    ) }

                    val storeProductList = repository.getWebCourseItem("50ad5624-86be-45b6-9337-372fa609a619",-1, 1, 0,6).data.asStoreProductList()
                    productStore.addProductList(storeProductList)
                    _state.update { it.copy(
                        popularProducts = storeProductList?.toList()
                    ) }

                } catch (e: Exception) {
                    Log.d("TAG", "getArticleFeed: ${e}")
                }
            }
            refreshing.value = false
        }
    }

}

data class WebHomeViewState(
    val highlightedArticle: Article? = null,
    val recommendedArticles: List<Article>? = null,
    val popularProducts: List<Product>? = null,
    val recentArticles: List<Article>? = null,
    val refreshing: Boolean = false,
)