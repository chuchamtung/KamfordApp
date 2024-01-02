package com.kamford.app.compose.question

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamford.app.compose.OfflineDialog

@Composable
fun KamfordQuestionApp(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    appState: KamfordQuestionAppState = rememberKamfordQuestionAppState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    if (appState.isOnline) {
        NavHost(
            modifier = modifier,
            navController = appState.navController,
            startDestination = QuestionScreen.QuestionHome.route
        ) {
            composable(QuestionScreen.QuestionHome.route) {

            }

        }
    }else{
        OfflineDialog{ appState.refreshOnline() }
    }
}
