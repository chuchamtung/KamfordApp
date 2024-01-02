package com.kamford.app.compose.web.service

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.courses.Course
import com.kamford.app.data.local.courses.CoursesStore
import com.kamford.app.data.local.courses.asCourseList
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import com.kamford.app.data.local.courses.asStoreCourse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val courseStore: CoursesStore = RoomModule.courseStore,
) : ViewModel() {
    private val courseId: String = "fdb84c25-0321-4402-8f60-7b83ebbcaf2d"
    private val refreshing = MutableStateFlow(false)

    private val repository = MainRepository()
    private val _selectedNextCourse = MutableStateFlow<Course?>(null)
    private val _state = MutableStateFlow(ServicesViewState())
    val state: StateFlow<ServicesViewState>
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
                ServicesViewState(
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

data class ServicesViewState(
    val nextCourseList: List<Course>? = emptyList(),
    val selectedNextCourse: Course? = null,
    val refreshing: Boolean = false,
)