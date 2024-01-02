package com.kamford.app.data.local.albums

import com.kamford.app.data.ResultsResponse
import com.kamford.app.data.local.attachments.AttachmentsResponse
import com.kamford.app.data.local.users.UsersResponse
import com.kamford.app.di.Constants
import com.squareup.moshi.Json


data class AlbumsResponse(
    @Json(name="id") var id: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "courseId") val courseId: String,
    @Json(name = "isType") val isType: Int,
    @Json(name = "tags") val tags: String?,
    @Json(name = "author") val author: String?,
    @Json(name = "imageId") val imageId: String?,

    @Json(name = "viewCount") val viewCount: Int,
    @Json(name = "repliesCount") val repliesCount: Int,
    @Json(name = "favoriteCount") val favoriteCount: Int,
    @Json(name = "bookmarkCount") val bookmarkCount: Int,

    @Json(name="authorItem") val authorItem: UsersResponse?,

    @Json(name="attachmentList") val attachmentList: List<AttachmentsResponse>?,
)

fun ResultsResponse.asStoreAlbumList() :Collection<Album>? = albumList?.map { it.asStoreAlbum() }

fun AlbumsResponse.asStoreAlbum() = Album(
    id = id,
    createdAt = createdAt,
    name = name,
    description = description,
    courseId = courseId,
    isType = isType,
    tags = tags,
    author = author,
    imageId = imageId,

    viewCount = viewCount,
    repliesCount = repliesCount,
    favoriteCount = favoriteCount,
    bookmarkCount = bookmarkCount,

    image_l_url = if (imageId != null ) Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_BIG + imageId else null,
    image_m_url = if (imageId != null ) Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_MEDIUM + imageId else null,
    image_s_url = if (imageId != null ) Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_SMALL + imageId else null,

    web_album_detail_url = Constants.KAMFORD_APP_WEB + "/portal/album/detail/" + id,
    blog_album_detail_url = Constants.KAMFORD_APP_BLOG + "author-" + author +"/album/" + id
)
