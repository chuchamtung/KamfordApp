package com.kamford.app.data

import android.net.Uri
import androidx.core.net.toFile
import com.kamford.app.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import java.io.File


class MainRepository {

    //WEB
    suspend fun getWebProductDetail(id: String): DataResponse
            = withContext(Dispatchers.IO) {
        return@withContext NetworkModule.kamfordApi.getWebProductDetail(id)
    }
    suspend fun getWebArticleDetail(id: String): DataResponse
            = withContext(Dispatchers.IO) {
        return@withContext NetworkModule.kamfordApi.getWebArticleDetail(id)
    }
    suspend fun getWebAlbumDetail(id: String): DataResponse
            = withContext(Dispatchers.IO) {
        return@withContext NetworkModule.kamfordApi.getWebAlbumDetail(id)
    }
    suspend fun getWebCourseList(): DataResponse
            = withContext(Dispatchers.IO) {
        return@withContext NetworkModule.kamfordApi.getWebCourseList()
    }
    suspend fun getWebCourseItem(id: String?, is_type: Int, is_order: Int, page_num: Int, page_size: Int): DataResponse
            = withContext(Dispatchers.IO) {
        return@withContext NetworkModule.kamfordApi.getWebCourseItem(id,is_type,is_order,page_num,page_size)
    }

    //USER
    suspend fun signinPost(username: String, password: String): DataResponse
            = withContext(Dispatchers.IO) {
        return@withContext NetworkModule.kamfordApi.signinPost(username,password)
    }

    suspend fun signupPost(username: String, password: String, nickname: String): DataResponse
            = withContext(Dispatchers.IO) {
        return@withContext NetworkModule.kamfordApi.signupPost(username,password,nickname)
    }

    suspend fun stateUsername(username: String): DataResponse
            = withContext(Dispatchers.IO) {
        return@withContext NetworkModule.kamfordApi.getNameState(username)
    }

    suspend fun stateNickname(nickname: String): DataResponse
            = withContext(Dispatchers.IO) {
        return@withContext NetworkModule.kamfordApi.getNickState(nickname)
    }


    suspend fun updateAvatarPost(imageUri: File, username: String): DataResponse
            = withContext(Dispatchers.IO) {
//        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
//            .addFormDataPart(
//                "imageFile",
//                imageFile.name,
//                imageFile.asRequestBody("image/*".toMediaTypeOrNull()))
//
//        val parts: MutableList<MultipartBody.Part> = ArrayList(files.size)
        val requestBody = imageUri.asRequestBody("image/*".toMediaTypeOrNull())
        val imageFile = MultipartBody.Part.createFormData("imageFile", imageUri.name, requestBody)

        return@withContext NetworkModule.kamfordApi.updateAvatarPost(username, imageFile)
    }


}