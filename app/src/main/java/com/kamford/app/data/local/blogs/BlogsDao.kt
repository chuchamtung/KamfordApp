package com.kamford.app.data.local.blogs

import androidx.room.Dao
import androidx.room.Query
import com.kamford.app.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [Blog] related operations.
 */
@Dao
abstract class BlogsDao : BaseDao<Blog> {

    @Query("SELECT * FROM blogs WHERE id = :id")
    abstract suspend fun getBlogById(id: String): Blog?

    @Query("SELECT * FROM blogs WHERE name = :name")
    abstract fun getBlogByName(name: String): Blog?

    @Query("SELECT * FROM blogs WHERE name = :name")
    abstract fun findBlogByName(name: String): Flow<Blog>

    @Query("SELECT * FROM blogs WHERE author = :author")
    abstract fun findBlogByAuthor(author: String): Flow<Blog>

}
