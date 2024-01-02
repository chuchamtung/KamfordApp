package com.kamford.app.compose.web.about

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.courses.Course
import com.kamford.app.data.local.courses.CoursesStore
import com.kamford.app.data.local.courses.asCourseList
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import com.kamford.app.data.local.courses.asStoreCourse
import com.kamford.app.data.local.courses.asStoreCourseList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AboutUsViewModel(
    private val courseStore: CoursesStore = RoomModule.courseStore,
) : ViewModel() {
    private val courseId: String = "1416602c-81d1-46ef-84b2-fafc20e373ae"
    private val refreshing = MutableStateFlow(false)

    private val repository = MainRepository()
    private val _selectedNextCourse = MutableStateFlow<Course?>(null)
    private val _state = MutableStateFlow(AboutUsViewState())
    val state: StateFlow<AboutUsViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                courseStore.findCourseListByParentId(courseId).onEach {
                    if (it.isEmpty()) {
                        refreshing.value = true
                    }
                    if (it.isNotEmpty() && _selectedNextCourse.value == null) {
                        _selectedNextCourse.value = it[0]
                    }
                },
                _selectedNextCourse,
                refreshing
            ) {nextCourseList, selectedNextCourse, refreshing ->
                AboutUsViewState(
                    nextCourseList = nextCourseList,
                    selectedNextCourse = selectedNextCourse,
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
                        val storeCourse = repository.getWebCourseItem(courseId,-1,0,0,0).data.courseItem
                        courseStore.addCourse(storeCourse?.asStoreCourse())
                        courseStore.addCourseList(storeCourse?.asCourseList())
                    } catch (e: Exception) {
                        Log.d("TAG", "getArticleFeed: ${e}")
                    }
                }
            }
            refreshing.value = false
        }

    }

    fun onNextCourseSelected(course: Course) {
        _selectedNextCourse.value = course
    }

}

data class AboutUsViewState(
    val nextCourseList: List<Course>? = null,
    val selectedNextCourse: Course? = null,
    val refreshing: Boolean = false,
)