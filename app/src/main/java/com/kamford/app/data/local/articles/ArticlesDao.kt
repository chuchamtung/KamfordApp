package com.kamford.app.data.local.articles

import androidx.room.Dao
import androidx.room.Query
import com.kamford.app.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [Article] related operations.
 */
@Dao
abstract class ArticlesDao : BaseDao<Article> {
    @Query(
        """
        SELECT * FROM articles
        WHERE course_id = :courseId AND author = :author
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun articleListByAuthorAndCourseId(
        author: String,
        courseId: String,
        limit: Int
    ): Flow<List<Article>>

    @Query(
        """
        SELECT * FROM articles
        WHERE is_type = :typeId AND author = :author
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun articleListByAuthorAndTypeId(
        author: String,
        typeId: Int,
        limit: Int
    ): Flow<List<Article>>

    @Query(
        """
        SELECT * FROM articles
        WHERE author = :author
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun articleListByAuthor(
        author: String,
        limit: Int
    ): Flow<List<Article>>

    @Query(
        """
        SELECT * FROM articles
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun articleListAll(
        limit: Int
    ): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE id = :id")
    abstract suspend fun getArticleById(id: String): Article?

    @Query("SELECT * FROM articles WHERE id = :id")
    abstract fun findArticleById(id: String): Flow<Article?>

    @Query("SELECT COUNT(*) FROM articles")
    abstract suspend fun count(): Int

}
