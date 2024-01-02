package com.kamford.app.compose.user.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.compose.user.TextFieldState
import com.kamford.app.compose.user.UsernameState
import com.kamford.app.data.MainRepository
import com.kamford.app.data.local.users.UsersStore
import com.kamford.app.data.local.users.asUser
import com.kamford.app.di.RoomModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val userStore: UsersStore = RoomModule.userStore
) : ViewModel() {

    private val repository = MainRepository()

    private val _state = MutableStateFlow(SignUpViewState())
    val state: StateFlow<SignUpViewState>
        get() = _state

    fun signUp(
        username: String,
        password: String,
        nickname: String,
        onSignUpComplete: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response = repository.signupPost(username,password,nickname)
                if (response.error == 0 && response.token != null){
                    val userData = response.data.userItem?.asUser()
                    if (userData != null){
                        userData.userKey = response.token
                        userData.loginState = 1
                        userData.androidId = RoomModule.androidID
                        userData.androidFinger = RoomModule.androidFinger
                        userData.androidAgent = System.getProperty("http.agent")
                        userStore.addUserById(userData)

                        onSignUpComplete()
                    }
                }else{
                    _state.update { it.copy(
                        stateSingup = true
                    ) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(
                    stateSingup = true
                ) }
                Log.d("TAG", "getArticleFeed: ${e}")
            }
        }
    }
}

data class SignUpViewState(
    val stateSingup: Boolean = false
)