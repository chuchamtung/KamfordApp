package com.kamford.app.data.local.replies

import androidx.room.Dao
import androidx.room.Query
import com.kamford.app.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [Reply] related operations.
 */
@Dao
abstract class RepliesDao : BaseDao<Reply> {

    @Query(
        """
        SELECT replies.* FROM replies
        WHERE author = :author
        LIMIT :limit
        """
    )
    abstract fun replyListByAuthor(
        author: String,
        limit: Int
    ): Flow<List<Reply>>

    @Query(
        """
        SELECT replies.* FROM replies
        WHERE quote_id = :quoteId
        LIMIT :limit
        """
    )
    abstract fun replyListByQuoteId(
        quoteId: String,
        limit: Int
    ): Flow<List<Reply>>

    @Query("SELECT * FROM replies WHERE id = :id")
    abstract suspend fun getReplyById(id: String): Reply?
}
