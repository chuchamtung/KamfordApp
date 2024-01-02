package com.kamford.app.compose.user

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamford.app.compose.OfflineDialog
import com.kamford.app.compose.user.home.UserHomeScreen
import com.kamford.app.compose.user.signin.SignInRoute
import com.kamford.app.compose.user.signup.SignUpRoute

@Composable
fun KamfordUserApp(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    appState: KamfordUserAppState = rememberKamfordUserAppState()
) {
    if (appState.isOnline) {
        NavHost(
            modifier = modifier,
            navController = appState.navController,
            startDestination = UserScreen.UserHome.route
        ) {
            composable(UserScreen.UserHome.route) {
                UserHomeScreen(
                    openDrawer = openDrawer,
                    onSignIn = appState::navigateToSignIn,
                    onSignUp = appState::navigateToSignUp
                )
            }

            composable(UserScreen.UserSignin.route) {
                SignInRoute(
                    onSignInSubmitted = appState::navigateToUserHome,
                    onNavUp = appState::navigateBack
                )
            }

            composable(UserScreen.UserSignup.route) {
                SignUpRoute(
                    onSignIn = appState::navigateToSignIn,
                    onSignUpSubmitted = appState::navigateToUserHome,
                    onNavUp = appState::navigateBack
                )
            }


        }
    }else{
        OfflineDialog{ appState.refreshOnline() }
    }
}
