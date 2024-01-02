package com.kamford.app.data.local.attachments

import androidx.room.Dao
import androidx.room.Query
import com.kamford.app.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [Attachment] related operations.
 */
@Dao
abstract class AttachmentsDao : BaseDao<Attachment> {
    @Query(
        """
        SELECT * FROM attachments
        WHERE quote_id = :quoteId
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun attachmentListByQuoteId(
        quoteId: String,
        limit: Int
    ): Flow<List<Attachment>>

    @Query(
        """
        SELECT * FROM attachments
        WHERE album_id = :albumId
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun attachmentListByAlbumId(
        albumId: String,
        limit: Int
    ): Flow<List<Attachment>>

    @Query(
        """
        SELECT * FROM attachments
        WHERE is_type = :typeId AND author = :author
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun attachmentListByAuthorAndTypeId(
        author: String,
        typeId: Int,
        limit: Int
    ): Flow<List<Attachment>>

    @Query("SELECT * FROM attachments WHERE id = :id")
    abstract suspend fun getAttachmentById(id: String): Attachment?
}
