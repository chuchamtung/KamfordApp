package com.kamford.app.compose.web.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import com.kamford.app.data.local.courses.Course
import com.kamford.app.data.local.courses.CoursesStore
import com.kamford.app.data.local.courses.asCourseList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val courseStore: CoursesStore = RoomModule.courseStore
) : ViewModel() {

    private val courseId: String = "50ad5624-86be-45b6-9337-372fa609a619"
    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()

    private val _selectedNextCourse = MutableStateFlow<Course?>(null)
    private val _state = MutableStateFlow(ProductsViewState())
    val state: StateFlow<ProductsViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                courseStore.courseListByParentId(courseId).onEach {
                    if ( it.isEmpty() ){
                        refreshing.value = true
                    }else if ( _selectedNextCourse.value == null ) {
                        _selectedNextCourse.value = it[0]
                    }
                },
                _selectedNextCourse,
                refreshing
            ) {courseList, selectedCourse, refreshing ->
                ProductsViewState(
                    nextCourseList = courseList,
                    selectedNextCourse = selectedCourse,
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
                        val courseList = repository.getWebCourseItem(courseId,-1,0,0,0).data.courseItem?.asCourseList()
                        courseStore.addCourseList(courseList)
                        ProductsViewState(
                            nextCourseList = courseList?.toList(),
                            selectedNextCourse = courseList?.toList()?.get(0)
                        )
                    } catch (e: Exception) {
                        Log.d("TAG", "getProductFeed: ${e}")
                    }
                }
                refreshing.value = false
            }
        }
    }

    fun onNextCourseSelected(course: Course) {
        _selectedNextCourse.value = course
    }

}

data class ProductsViewState(
    val nextCourseList: List<Course>? = emptyList(),
    val selectedNextCourse: Course? = null,
    val refreshing: Boolean = false,
)