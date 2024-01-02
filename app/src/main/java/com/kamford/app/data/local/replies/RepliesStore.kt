package com.kamford.app.data.local.replies

import kotlinx.coroutines.flow.Flow


/**
 * @description
 * @author chamtungchu
 * @date 2023/9/25
 * @version 1.0
 */
class RepliesStore(
    private val repliesDao: RepliesDao,
) {


    fun replyListByAuthor(
        author: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Reply>> {
        return repliesDao.replyListByAuthor(author,limit)
    }


    fun replyListByQuoteId(
        quoteId: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Reply>> {
        return repliesDao.replyListByQuoteId(quoteId,limit)
    }

    suspend fun addReply(reply: Reply): String {
        return when (val local = repliesDao.getReplyById(reply.id)) {
            null -> repliesDao.insert(reply).toString()
            else -> local.id
        }
    }
}