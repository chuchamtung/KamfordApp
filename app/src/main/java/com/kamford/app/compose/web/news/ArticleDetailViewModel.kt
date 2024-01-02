package com.kamford.app.compose.web.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.articles.Article
import com.kamford.app.data.local.articles.ArticlesStore
import com.kamford.app.data.local.articles.asStoreArticle
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import com.kamford.app.data.local.courses.Course
import com.kamford.app.data.local.courses.CoursesStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    private val articleId: String?,
    private val articleStore: ArticlesStore = RoomModule.articleStore,
    private val courseStore: CoursesStore = RoomModule.courseStore,
) : ViewModel() {

    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()

    private val _selectedArticleCourse = MutableStateFlow<Course?>(null)
    private val _state = MutableStateFlow(ArticleDetailViewState())
    val state: StateFlow<ArticleDetailViewState>
        get() = _state

    init {
        viewModelScope.launch {
            if (articleId != null){
                combine(
                    articleStore.findArticleById(articleId).onEach {
                        if (it == null){
                            refreshing.value = true
                        }else if (it.courseId != null && _selectedArticleCourse.value == null) {
                            _selectedArticleCourse.value = courseStore.getCourseById(it.courseId!!)
                        }
                    },
                    _selectedArticleCourse,
                    refreshing
                ) {articleFlow, coursereFlow, refreshing ->
                    ArticleDetailViewState(
                        selectedArticle = articleFlow,
                        articleCourse = coursereFlow,
                        refreshing = refreshing
                    )
                }.collect {
                    _state.value = it
                    refresh(_state.value.refreshing)
                }
            }
        }
    }

    private fun refresh(force: Boolean) {
        if (force && !articleId.isNullOrEmpty()){
            viewModelScope.launch {
                runCatching {
                    try {
                        val storeArticle = repository.getWebArticleDetail(articleId).data.articleItem?.asStoreArticle()
                        articleStore.addArticle(storeArticle)
                        ArticleDetailViewState(
                            selectedArticle = storeArticle
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

data class ArticleDetailViewState(
    val selectedArticle: Article? = null,
    val articleCourse: Course? = null,
    val refreshing: Boolean = false
)