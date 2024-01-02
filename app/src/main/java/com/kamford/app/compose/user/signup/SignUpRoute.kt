package com.kamford.app.compose.user.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.compose.utils.viewModelProviderFactoryOf

@Composable
fun SignUpRoute(
    modifier: Modifier = Modifier,
    onSignUpSubmitted: () -> Unit,
    onNavUp: () -> Unit,
    onSignIn: () -> Unit,
) {
    val signUpViewModel: SignUpViewModel = viewModel(factory = viewModelProviderFactoryOf { SignUpViewModel() })
    SignUpScreen(
        onSignUpSubmitted = { username, password, nickname ->
            signUpViewModel.signUp(username, password, nickname, onSignUpSubmitted)
        },
        onNavUp = onNavUp,
        onSignIn = onSignIn
    )
}
