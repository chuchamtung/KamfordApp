package com.kamford.app.compose.user.signin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignInRoute(
    modifier: Modifier = Modifier,
    onSignInSubmitted: () -> Unit,
    onNavUp: () -> Unit,
) {
    val signInViewModel: SignInViewModel = viewModel(factory = SignInViewModelFactory())
    SignInScreen(
        onSignInSubmitted = { username, password ->
            signInViewModel.signIn(username, password, onSignInSubmitted)
        },
        onNavUp = onNavUp,
    )
}
