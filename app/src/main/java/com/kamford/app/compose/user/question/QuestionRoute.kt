package com.kamford.app.compose.user.question

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.compose.utils.viewModelProviderFactoryOf

@Composable
fun QuestionRoute(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: (username: String) -> Unit,
    onNavigateToSignUp: (username: String) -> Unit,
    onSignInAsGuest: () -> Unit,
) {
    val viewModel: QuestionsViewModel = viewModel(
        key = "question",
        factory = viewModelProviderFactoryOf { QuestionsViewModel() }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    if (viewState.userData?.loginState == 1){

    }else{

    }
}
