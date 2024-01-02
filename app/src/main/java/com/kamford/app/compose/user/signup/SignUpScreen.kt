package com.kamford.app.compose.user.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.compose.user.Password
import com.kamford.app.compose.user.SignInSignUpScreen
import com.kamford.app.compose.user.SignInSignUpTopAppBar
import com.kamford.app.compose.user.Username
import com.kamford.app.compose.user.ConfirmPasswordState
import com.kamford.app.compose.user.Nickname
import com.kamford.app.compose.user.NicknameState
import com.kamford.app.compose.user.PasswordState
import com.kamford.app.compose.user.TextFieldError
import com.kamford.app.compose.user.TextFieldState
import com.kamford.app.compose.user.UsernameState
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.compose.web.about.AboutUsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onSignUpSubmitted: (username: String, password: String, nickname: String) -> Unit,
    onNavUp: () -> Unit,
    onSignIn: () -> Unit,
) {
    val viewModel: SignUpViewModel = viewModel(factory = viewModelProviderFactoryOf { SignUpViewModel() })
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SignInSignUpTopAppBar(
                topAppBarText = "create account",
                onNavUp = onNavUp,
            )
        },
        content = { contentPadding ->
            SignInSignUpScreen(
                contentPadding = contentPadding,
            ) {
                Column {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        if (viewState.stateSingup){
                            Column(
                                modifier = modifier,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextFieldError(textError = "Username Or Nickname Create Error!!")
                                Text(
                                    text = "or",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    modifier = Modifier.paddingFromBaseline(top = 16.dp)
                                )
                                Button(
                                    onClick = onSignIn,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "sign in")
                                }
                            }
                        }


                        val passwordFocusRequest = remember { FocusRequester() }
                        val confirmationPasswordFocusRequest = remember { FocusRequester() }

                        val usernameState = remember { UsernameState() }
                        Username(usernameState, onImeAction = { passwordFocusRequest.requestFocus() })

                        val nicknameState = remember { NicknameState() }
                        Nickname(nicknameState, onImeAction = { passwordFocusRequest.requestFocus() })

                        Spacer(modifier = Modifier.height(16.dp))
                        val passwordState = remember { PasswordState() }
                        Password(
                            label = "Password",
                            passwordState = passwordState,
                            imeAction = ImeAction.Next,
                            onImeAction = { confirmationPasswordFocusRequest.requestFocus() },
                            modifier = Modifier.focusRequester(passwordFocusRequest)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        val confirmPasswordState = remember { ConfirmPasswordState(passwordState = passwordState) }
                        Password(
                            label = "Confirm Password",
                            passwordState = confirmPasswordState,
                            onImeAction = { onSignUpSubmitted(usernameState.text, passwordState.text, nicknameState.text) },
                            modifier = Modifier.focusRequester(confirmationPasswordFocusRequest)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "terms and conditions",
                            style = MaterialTheme.typography.bodySmall,
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { onSignUpSubmitted(usernameState.text, passwordState.text, nicknameState.text) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = usernameState.isValid && nicknameState.isValid &&
                                    passwordState.isValid &&
                                    confirmPasswordState.isValid
                        ) {
                            Text(text = "create account")
                        }
                    }
                }
            }
        }
    )
}

