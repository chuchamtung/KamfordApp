package com.kamford.app.data

import com.kamford.app.data.local.albums.AlbumsResponse
import com.kamford.app.data.local.articles.ArticlesResponse
import com.kamford.app.data.local.courses.CoursesResponse
import com.kamford.app.data.local.products.ProductsResponse
import com.kamford.app.data.local.users.UsersResponse
import com.squareup.moshi.Json

data class DataResponse (
    @Json(name="error") val error: Int,
    @Json(name="data") val data: ResultsResponse,
    @Json(name="token") val token: String?,
    @Json(name="message") val message: String?,
)

data class ResultsResponse(
    @Json(name="pageNum") val pageNum: Int?,
    @Json(name="pageCount") val pageCount: Int?,
    @Json(name="pageSize") val pageSize: Int?,
    @Json(name="state") val state: Boolean?,

    @Json(name="ArticleList") val articleList: List<ArticlesResponse>?,
    @Json(name="ArticleItem") val articleItem: ArticlesResponse?,

    @Json(name="CourseItem") val courseItem: CoursesResponse?,
    @Json(name="CourseList") val courseList: List<CoursesResponse>?,

    @Json(name="ProductItem") val productItem: ProductsResponse?,
    @Json(name="ProductList") val productList: List<ProductsResponse>?,

    @Json(name="AlbumItem") val albumItem: AlbumsResponse?,
    @Json(name="AlbumList") val albumList: List<AlbumsResponse>?,

    @Json(name="UserItem") val userItem: UsersResponse?,
)




