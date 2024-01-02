package com.kamford.app.compose.user.home

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.compose.user.question.QuestionsViewState
import com.kamford.app.data.MainRepository
import com.kamford.app.data.local.users.User
import com.kamford.app.data.local.users.UsersStore
import com.kamford.app.data.local.users.asUser
import com.kamford.app.di.RoomModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class UserHomeViewModel (
    private val userStore: UsersStore = RoomModule.userStore,
    private val photoUriManager: PhotoUriManager
) : ViewModel() {
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
            userStore.findUserByAndroidId(RoomModule.androidFinger).collect {
                QuestionsViewState(
                    userData = it
                )
            }
        }
    }

    fun updateAvatar(fileUri: File?, username: String?) {
        if (fileUri != null && username != null){
            viewModelScope.launch {
                try {
                    val response = repository.updateAvatarPost(fileUri,username)
                    if (response.error == 0 && response.data.userItem != null){
                        val userData = response.data.userItem.asUser()
                        userData.userKey = response.token
                        userData.loginState = 1
                        userData.androidId = RoomModule.androidID
                        userData.androidFinger = RoomModule.androidFinger
                        userData.androidAgent = System.getProperty("http.agent")
                        userStore.addUserById(userData)

                        userStore.findUserByAndroidId(RoomModule.androidFinger).collect {
                            QuestionsViewState(
                                userData = it
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.d("TAG", "getArticleFeed: ${e}")
                }
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
    val userData: User? = null
)
