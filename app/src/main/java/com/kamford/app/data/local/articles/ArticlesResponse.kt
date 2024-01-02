package com.kamford.app.data.local.articles

import com.kamford.app.data.ResultsResponse
import com.kamford.app.data.local.attachments.AttachmentsResponse
import com.kamford.app.data.local.users.UsersResponse
import com.kamford.app.di.Constants
import com.squareup.moshi.Json


data class ArticlesResponse(
    @Json(name="id") var id: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "courseId") val courseId: String?,
    @Json(name = "isType") val isType: Int,
    @Json(name = "tags") val tags: String?,
    @Json(name = "author") val author: String?,
    @Json(name = "imageId") val imageId: String?,
    @Json(name = "textId") val textId: String?,

    @Json(name = "viewCount") val viewCount: Int,
    @Json(name = "repliesCount") val repliesCount: Int,
    @Json(name = "favoriteCount") val favoriteCount: Int,
    @Json(name = "bookmarkCount") val bookmarkCount: Int,
    @Json(name = "ipAddress") val ipAddress: String?,
    @Json(name = "ipResource") val ipResource: String?,
    @Json(name = "client") val client: String?,

    @Json(name="authorItem") val authorItem: UsersResponse?,

    @Json(name="attachmentList") val attachmentList: List<AttachmentsResponse>?,
)


fun ResultsResponse.asStoreArticleList() :Collection<Article>? = articleList?.map { it.asStoreArticle() }

fun ArticlesResponse.asStoreArticle() = Article(
    id = id,
    createdAt = createdAt,
    name = name,
    description = description,
    courseId = courseId,
    isType = isType,
    tags = tags,
    author = author,
    imageId = imageId,
    textId = textId,

    viewCount = viewCount,
    repliesCount = repliesCount,
    favoriteCount = favoriteCount,
    bookmarkCount = bookmarkCount,
    ipAddress = ipAddress,
    ipResource = ipResource,
    client = client,

    image_l_url = if (imageId != null ) Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_BIG + imageId else null,
    image_m_url = if (imageId != null ) Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_MEDIUM + imageId else null,
    image_s_url = if (imageId != null ) Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_SMALL + imageId else null,

    web_article_detail_url = Constants.KAMFORD_APP_WEB + "/portal/article/detail/" + id,
    blog_article_detail_url = Constants.KAMFORD_APP_BLOG + "author-" + author +"/article/" + id
)