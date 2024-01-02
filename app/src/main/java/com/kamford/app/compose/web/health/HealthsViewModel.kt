package com.kamford.app.compose.web.health

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

class HealthsViewModel(
    private val articleStore: ArticlesStore = RoomModule.articleStore
) : ViewModel() {

    private val courseId: String = "87c12b66-7969-4164-80b4-662aff23650c"
    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()

    private val _state = MutableStateFlow(HealthsViewState())
    val state: StateFlow<HealthsViewState>
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
                HealthsViewState(
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
                        HealthsViewState(
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

data class HealthsViewState(
    val articleList: List<Article>? = emptyList(),
    val refreshing: Boolean = false,
)