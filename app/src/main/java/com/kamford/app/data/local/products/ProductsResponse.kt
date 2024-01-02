package com.kamford.app.data.local.products

import com.kamford.app.data.ResultsResponse
import com.kamford.app.data.local.attachments.AttachmentsResponse
import com.kamford.app.di.Constants
import com.squareup.moshi.Json


data class ProductsResponse(
    @Json(name="id") var id: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "price") val price: Double?,
    @Json(name = "brand") val brand: String?,
    @Json(name = "model") val model: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "courseId") val courseId: String,
    @Json(name = "isType") val isType: Int,
    @Json(name = "tags") val tags: String?,
    @Json(name = "author") val author: String?,
    @Json(name = "imageId") val imageId: String?,
    @Json(name = "textId") val textId: String?,

    @Json(name = "viewCount") val viewCount: Int,
    @Json(name = "repliesCount") val repliesCount: Int,
    @Json(name = "favoriteCount") val favoriteCount: Int,
    @Json(name = "bookmarkCount") val bookmarkCount: Int,


    @Json(name="attachmentList") val attachmentList: List<AttachmentsResponse>?,
)
fun ResultsResponse.asStoreProductList() :Collection<Product>? = productList?.map { it.asStoreProduct() }

fun ProductsResponse.asStoreProduct() = Product(
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

    image_l_url = if (imageId != null ) Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_BIG + imageId else null,
    image_m_url = if (imageId != null ) Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_MEDIUM + imageId else null,
    image_s_url = if (imageId != null ) Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_SMALL + imageId else null,

    web_product_detail_url = Constants.KAMFORD_APP_WEB + "/portal/product/detail/" + id
)