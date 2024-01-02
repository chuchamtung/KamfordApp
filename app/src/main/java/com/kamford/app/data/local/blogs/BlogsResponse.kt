package com.kamford.app.data.local.blogs

import com.squareup.moshi.Json


data class BlogsResponse(
    @Json(name = "id") val id: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "updatedAt") val updatedAt: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "alias") val alias: String?,
    @Json(name = "hosts") val hosts: String?,
    @Json(name = "logo") val logo: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "isValue") val isValue: Int,
    @Json(name = "isType") val isType: Int,
    @Json(name = "tags") val tags: String?,
    @Json(name = "author") val author: String?
)

fun BlogsResponse.asBlog() = Blog(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    name = name,
    alias = alias,
    hosts = hosts,
    logo = logo,
    description = description,
    isValue = isValue,
    isType = isType,
    tags = tags,
    author = author
)