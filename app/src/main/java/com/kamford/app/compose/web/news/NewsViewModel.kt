package com.kamford.app.compose.web.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.articles.Article
import com.kamford.app.data.local.articles.ArticlesStore
import com.kamford.app.data.local.articles.asStoreArticleList
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewsViewModel(
    private val articleStore: ArticlesStore = RoomModule.articleStore
) : ViewModel() {

    private val courseId: String = "ce215649-86f3-4d1e-917b-8547c0bdef6b"
    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()

    private val _state = MutableStateFlow(NewsViewState())
    val state: StateFlow<NewsViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                articleStore.articleListByCourseId(
                    author = "kamford_web",
                    courseId = courseId,
                    limit = Integer.MAX_VALUE
                ).onEach {
                    if (it.isEmpty()) {
                        refreshing.value = true
                    }
                },
                refreshing
            ) {articleList, refreshing ->
                NewsViewState(
                    articleList = articleList,
                    refreshing = refreshing
                )
            }.collect {
                _state.value = it
                refresh(_state.value.refreshing)
            }
        }
    }

    private fun refresh(force: Boolean) {
        if (force){
            viewModelScope.launch {
                runCatching {
                    try {
                        val storeArticleList = repository.getWebCourseItem(courseId,0,0,0,20).data.asStoreArticleList()
                        articleStore.addArticleList(storeArticleList)
                        NewsViewState(
                            articleList = storeArticleList?.toList()
                        )
                    } catch (e: Exception) {
                        Log.d("TAG", "getArticleFeed: ${e}")
                    }
                }
                refreshing.value = false
            }
        }
    }

}

data class NewsViewState(
    val articleList: List<Article>? = emptyList(),
    val refreshing: Boolean = false,
)