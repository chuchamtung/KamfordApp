package com.kamford.app.di

import com.kamford.app.data.DataResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface KamfordServiceApi {

    //WEB
    @GET("web/course")
    suspend fun getWebCourseList(): DataResponse
    @GET("web/course/{id}")
    suspend fun getWebCourseItem(
        @Path("id") id: String?,
        @Query("is_type") is_type: Int,
        @Query("is_order") is_order: Int,
        @Query("page_num") page_num: Int,
        @Query("page_size") page_size: Int
    ): DataResponse
    @GET("web/album-detail/{id}")
    suspend fun getWebAlbumDetail(@Path("id") id: String): DataResponse
    @GET("web/product-detail/{id}")
    suspend fun getWebProductDetail(@Path("id") id: String): DataResponse
    @GET("web/article-detail/{id}")
    suspend fun getWebArticleDetail(@Path("id") id: String): DataResponse


    //USER
    @POST("user/signin")
    suspend fun signinPost(@Query("username") username: String, @Query("password") password: String): DataResponse
    @POST("user/signup")
    suspend fun signupPost(@Query("username") username: String, @Query("password") password: String, @Query("nickname") nickname: String): DataResponse
    @GET("user/name-state/{name}")
    suspend fun getNameState(@Path("name") username: String): DataResponse
    @GET("user/nick-state")
    suspend fun getNickState(@Query("nickname") nickname: String): DataResponse
    @Multipart
    @POST("user/update/avatar")
    suspend fun updateAvatarPost(
        @Query("username") username: String,
        @Part imageFile: MultipartBody.Part
    ): DataResponse
    @GET("user/my-state/{name}")
    suspend fun getMyState(@Path("name") username: String): DataResponse

    // @GET  @POST  @PUT  @DELETE  @PATH  @HEAD  @OPTIONS  @HTTP
    // @FormUrlEncoded  @Multipart   @Streaming
    // @Query  @QueryMap  @Body  @Field  @FieldMap  @Part  @PartMap
    // @Path  @Header  @Headers  @Url



    @GET("course/{id}/page/{pageNum}/{pageSize}")
    suspend fun getArticleListByAuthorOrCourseOrPage(
        @Path("id") id: Long,
        @Path("pageNum") pageNum: Int,
        @Path("pageSize") pageSize: Int,
        @Query("isorder") isorder: Int,
        @Query("ishot") ishot: Int,
        @Query("istype") istype: Int,
        @Query("author") author: String?
    ): DataResponse

    @GET("article/{id}")
    suspend fun getArticle(@Path("id") id: Long): DataResponse

    @GET("search/article")
    suspend fun searchArticles(@Query("query") query: String,@Query("pageSize") pageSize: Int): DataResponse

    @GET("search")
    suspend fun getSearchList(@Query("pageSize") pageSize: Int): DataResponse

    @GET("tags")
    suspend fun getTagsList(@Query("pageSize") pageSize: Int): DataResponse

    @GET("course")
    suspend fun getCourseList(): DataResponse




}