package com.kamford.app.data.local.attachments

import com.kamford.app.data.local.albums.AlbumsResponse
import com.kamford.app.data.local.products.ProductsResponse
import com.kamford.app.di.Constants
import com.squareup.moshi.Json


data class AttachmentsResponse(
    @Json(name="id") var id: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "quoteId") val quoteId: String?,
    @Json(name = "albumId") val albumId: String?,
    @Json(name = "isType") val isType: Int,
    @Json(name = "author") val author: String?,
    @Json(name = "viewCount") val viewCount: Int
)

fun ProductsResponse.asStoreAttachmentList() :Collection<Attachment>? = attachmentList?.map { it.asStoreAttachment() }
fun AlbumsResponse.asStoreAttachmentList() :Collection<Attachment>? = attachmentList?.map { it.asStoreAttachment() }

fun AttachmentsResponse.asStoreAttachment() = Attachment(
    id = id,
    createdAt = createdAt,
    name = name,
    quoteId = quoteId,
    albumId = albumId,
    isType = isType,
    author = author,
    viewCount= viewCount,

    image_l_url = Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_BIG + id,
    image_m_url = Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_MEDIUM + id,
    image_s_url = Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_SMALL + id
)