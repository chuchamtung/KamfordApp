package com.kamford.app.data.local.attachments

import kotlinx.coroutines.flow.Flow

/**
 * @description
 * @author chamtungchu
 * @date 2023/9/25
 * @version 1.0
 */
class AttachmentsStore(
    private val attachmentsDao: AttachmentsDao,
) {

    fun attachmentListByQuoteId(
        quoteId: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Attachment>> {
        return attachmentsDao.attachmentListByQuoteId(quoteId,limit)
    }

    fun attachmentListByAlbumId(
        albumId: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Attachment>> {
        return attachmentsDao.attachmentListByAlbumId(albumId,limit)
    }



    fun attachmentListByAuthorAndTypeId(
        author: String,
        typeId: Int,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Attachment>> {
        return attachmentsDao.attachmentListByAuthorAndTypeId(author,typeId,limit)
    }

    suspend fun addAttachment(attachment: Attachment): String {
        return when (val local = attachmentsDao.getAttachmentById(attachment.id)) {
            null -> attachmentsDao.insert(attachment).toString()
            else -> local.id
        }
    }

    suspend fun addAttachmentList(attachments: Collection<Attachment>?) {
        if (!attachments.isNullOrEmpty()){
            attachmentsDao.insertAll(attachments)
        }
    }
}