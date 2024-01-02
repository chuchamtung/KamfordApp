package com.kamford.app.data.local.users

import com.kamford.app.data.local.blogs.BlogsResponse
import com.squareup.moshi.Json


data class UsersResponse(
    @Json(name="id") var id: String,
    @Json(name="name") var name: String,
    @Json(name="createdAt") var createdAt: Long,
    @Json(name="nickName") var nickName: String?,
    @Json(name="description") var description: String?,
    @Json(name="avatar") var avatar: String?,
    @Json(name="isBlog") val isBlog: Int,
    @Json(name="blogName") var blogName: String?,
    @Json(name="blogAlias") var blogAlias: String?,

    @Json(name="blogItem") val blogItem: BlogsResponse?
)

fun UsersResponse.asUser() = User(
    id = id,
    createdAt = createdAt,
    name = name,
    nickName = nickName,
    description = description,
    avatar = avatar,
    isBlog = isBlog,
    blogName = if (isBlog == 1) blogItem?.name else null,
    blogAlias = if (isBlog == 1) blogItem?.alias else null,
    androidId = null,
    androidFinger = null,
    androidAgent = null,
    userKey = null,
    loginState = 0,
)