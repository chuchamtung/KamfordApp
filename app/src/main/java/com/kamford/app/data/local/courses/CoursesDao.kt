package com.kamford.app.data.local.courses

import androidx.room.Dao
import androidx.room.Query
import com.kamford.app.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [Course] related operations.
 */
@Dao
abstract class CoursesDao : BaseDao<Course> {

    @Query(
        """
        SELECT courses.* FROM courses
        WHERE author = :author AND parent_id IS NULL
        LIMIT :limit
        """
    )
    abstract fun courseListByAuthor(
        author: String,
        limit: Int
    ): Flow<List<Course>>

    @Query(
        """
        SELECT courses.* FROM courses 
        WHERE id = :parentId OR parent_id = :parentId 
        LIMIT :limit
        """
    )
    abstract fun findCourseListByParentId(
        parentId: String,
        limit: Int
    ): Flow<List<Course>>

    @Query(
        """
        SELECT courses.* FROM courses 
        WHERE parent_id = :parentId 
        LIMIT :limit
        """
    )
    abstract fun courseListByParentId(
        parentId: String,
        limit: Int
    ): Flow<List<Course>>


    @Query("SELECT * FROM courses WHERE id = :id")
    abstract suspend fun getCourseById(id: String): Course?

    @Query("SELECT * FROM courses WHERE id = :id")
    abstract fun findCourseById(id: String): Flow<Course>
}
