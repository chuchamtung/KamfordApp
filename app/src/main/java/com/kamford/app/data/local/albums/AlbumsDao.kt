package com.kamford.app.data.local.albums

import androidx.room.Dao
import androidx.room.Query
import com.kamford.app.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [Albums] related operations.
 */
@Dao
abstract class AlbumsDao : BaseDao<Album> {
    @Query(
        """
        SELECT * FROM albums
        WHERE course_id = :courseId AND author = :author
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun albumListByAuthorAndCourseId(
        author: String,
        courseId: String,
        limit: Int
    ): Flow<List<Album>>

    @Query(
        """
        SELECT * FROM albums
        WHERE is_type = :typeId AND author = :author
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun albumListByAuthorAndTypeId(
        author: String,
        typeId: Int,
        limit: Int
    ): Flow<List<Album>>

    @Query(
        """
        SELECT * FROM albums
        WHERE author = :author
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun albumListByAuthor(
        author: String,
        limit: Int
    ): Flow<List<Album>>

    @Query(
        """
        SELECT * FROM albums
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun albumListAll(
        limit: Int
    ): Flow<List<Album>>

    @Query("SELECT * FROM albums WHERE id = :id")
    abstract suspend fun getAlbumById(id: String): Album?

    @Query("SELECT * FROM albums WHERE id = :id")
    abstract fun findAlbumById(id: String): Flow<Album>

    @Query("SELECT COUNT(*) FROM albums")
    abstract suspend fun count(): Int

}
