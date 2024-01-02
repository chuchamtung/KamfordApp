package com.kamford.app.compose.user.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.users.User
import com.kamford.app.data.local.users.UsersStore
import com.kamford.app.data.MainRepository
import com.kamford.app.di.RoomModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class QuestionsViewModel(
    private val userStore: UsersStore = RoomModule.userStore,
) : ViewModel() {
    private val refreshing = MutableStateFlow(false)

    private val repository = MainRepository()
    private val _state = MutableStateFlow(QuestionsViewState())
    val state: StateFlow<QuestionsViewState>
        get() = _state

    init {
        viewModelScope.launch {
            userStore.findUserByAndroidId(RoomModule.androidFinger).collect {
                QuestionsViewState(
                    userData = it
                )
            }
        }
    }


    fun handleContinue(
        username: String,
        onNavigateToSignIn: (username: String) -> Unit,
        onNavigateToSignUp: (username: String) -> Unit,
    ) {

        onNavigateToSignIn(username)

//        if (userRepository.isKnownUserEmail(email)) {
//            onNavigateToSignIn(username)
//        } else {
//            onNavigateToSignUp(username)
//        }
    }

    fun signInAsGuest(
        onSignInComplete: () -> Unit,
    ) {
        //userRepository.signInAsGuest()
        onSignInComplete()
    }
}


data class QuestionsViewState(
    val userData: User? = null,
    val refreshing: Boolean = false,
)
