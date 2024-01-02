package com.kamford.app.compose.user.home

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.MainRepository
import com.kamford.app.data.local.users.User
import com.kamford.app.data.local.users.UsersStore
import com.kamford.app.di.RoomModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File


class UserHomeViewModel (
    private val userStore: UsersStore = RoomModule.userStore,
    private val photoUriManager: PhotoUriManager
) : ViewModel() {
    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()

    private val _state = MutableStateFlow(UserHomeViewState())
    val state: StateFlow<UserHomeViewState>
        get() = _state


    private val _selfieUri = mutableStateOf<Uri?>(null)
    private val _selFile = mutableStateOf<File?>(null)
    val selfieUri
        get() = _selfieUri.value
    val selFile
        get() = _selFile.value


    init {
        viewModelScope.launch {
            combine(
                userStore.findUserByAndroidId(RoomModule.androidFinger).onEach {
                    if (it == null){
                        refreshing.value = true
                    }
                },
                refreshing
            ) {userData, refreshing ->
                UserHomeViewState(
                    userData = userData,
                    refreshing = refreshing
                )
            }.collect {
                _state.value = it
                refresh(_state.value.refreshing)
            }
        }
    }

    private fun refresh(force: Boolean) {
        if (force){
//            viewModelScope.launch {
//                try {
//
//                } catch (e: Exception) {
//                    Log.d("TAG", "getArticleFeed: ${e}")
//                }
//            }
            refreshing.value = false
        }
    }

    fun updateAvatar(fileUri: File) {
        viewModelScope.launch {
            try {
                repository.updateAvatarPost(fileUri,"darcy")
            } catch (e: Exception) {
                Log.d("TAG", "getArticleFeed: ${e}")
            }
        }
    }

    fun getNewSelfie() = photoUriManager.buildNewUri()

    fun onSelfieResponse(photoState: PhotoState) {
        _selfieUri.value = photoState.uri
        _selFile.value = photoState.file
    }





}

data class UserHomeViewState(
    val userData: User? = null,
    val refreshing: Boolean = false,
)

//
//@Stable
//@Parcelize
//data class MediaResource(
//    internal val id: Long,
//    internal val bucketId: String,
//    internal val bucketName: String,
//    val uri: Uri,
//    val path: String,
//    val name: String,
//    val mimeType: String,
//) : Parcelable {
//
//    val isImage: Boolean
//        get() = mimeType.startsWith(prefix = "image")
//
//    val isVideo: Boolean
//        get() = mimeType.startsWith(prefix = "video")
//
//}
//
//enum class MimeType(val type: String) {
//    JPEG(type = "image/jpeg"),
//    PNG(type = "image/png"),
//    WEBP(type = "image/webp"),
//    HEIC(type = "image/heic"),
//    HEIF(type = "image/heif"),
//    BMP(type = "image/x-ms-bmp"),
//    GIF(type = "image/gif"),
//    MPEG(type = "video/mpeg"),
//    MP4(type = "video/mp4"),
//    QUICKTIME(type = "video/quicktime"),
//    THREEGPP(type = "video/3gpp"),
//    THREEGPP2(type = "video/3gpp2"),
//    MKV(type = "video/x-matroska"),
//    WEBM(type = "video/webm"),
//    TS(type = "video/mp2ts"),
//    AVI(type = "video/avi");
//
//    companion object {
//
//        fun ofAll(hasGif: Boolean = true): Set<MimeType> {
//            return if (hasGif) {
//                entries.toSet()
//            } else {
//                entries.filter { it != GIF }.toSet()
//            }
//        }
//
//        fun ofImage(hasGif: Boolean = true): Set<MimeType> {
//            return if (hasGif) {
//                entries.filter { it.isImage }
//            } else {
//                entries.filter { it.isImage && it != GIF }
//            }.toSet()
//        }
//
//        fun ofVideo(): Set<MimeType> {
//            return entries.filter { it.isVideo }.toSet()
//        }
//
//    }
//
//    internal val isImage: Boolean
//        get() = type.startsWith(prefix = "image")
//
//    internal val isVideo: Boolean
//        get() = type.startsWith(prefix = "video")
//
//}
