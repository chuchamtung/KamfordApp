package com.kamford.app.data.local.courses

import kotlinx.coroutines.flow.Flow


/**
 * @description
 * @author chamtungchu
 * @date 2023/9/25
 * @version 1.0
 */
class CoursesStore(
    private val coursesDao: CoursesDao,
) {

    fun findCourseListByAuthor(
        author: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Course>> {
        return coursesDao.courseListByAuthor(author,limit)
    }


    fun findCourseListByParentId(
        parentId: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Course>> {
        return coursesDao.findCourseListByParentId(parentId,limit)
    }

    fun courseListByParentId(
        parentId: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Course>> {
        return coursesDao.courseListByParentId(parentId,limit)
    }

    suspend fun addCourse(course: Course?): String {
        if (course != null ){
            return when (val local = coursesDao.getCourseById(course.id)) {
                null -> coursesDao.insert(course).toString()
                else -> {
                    coursesDao.update(course)
                    local.id
                }
            }
        }
        return ""
    }


    suspend fun addCourseList(courses: Collection<Course>?) {
        if (!courses.isNullOrEmpty()){
            coursesDao.insertAll(courses)
        }

    }

    fun findCourseById(id: String): Flow<Course> {
        return coursesDao.findCourseById(id)
    }

    suspend fun getCourseById(id: String): Course? {
        return coursesDao.getCourseById(id)
    }
}