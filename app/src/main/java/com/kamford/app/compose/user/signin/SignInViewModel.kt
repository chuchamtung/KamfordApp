package com.kamford.app.compose.user.signin

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.users.User
import com.kamford.app.data.local.users.UsersStore
import com.kamford.app.data.local.users.asUser
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import kotlinx.coroutines.launch

class SignInViewModel(
    private val userStore: UsersStore = RoomModule.userStore
) : ViewModel() {

    var isLoading: Boolean by mutableStateOf(false)
    var errorMessage: Int? by mutableStateOf(null)
    private val repository = MainRepository()

    fun signIn(
        username: String,
        password: String,
        onSignInComplete: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response = repository.signinPost(username,password)
                if (response.data.userItem != null && !response.token.isNullOrEmpty()){
                    val userData = response.data.userItem.asUser()
                    userData.userKey = response.token
                    userData.loginState = 1
                    userData.androidId = RoomModule.androidID
                    userData.androidFinger = RoomModule.androidFinger
                    userData.androidAgent = System.getProperty("http.agent")
                    userStore.addUserById(userData)

                    onSignInComplete()
                }
            } catch (e: Exception) {
                Log.d("TAG", "getArticleFeed: ${e}")
            }
        }

    }
}

class SignInViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
