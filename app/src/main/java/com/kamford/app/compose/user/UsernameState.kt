package com.kamford.app.compose.user

import com.kamford.app.data.MainRepository
import java.util.regex.Pattern

//private const val USERNAME_VALIDATION_REGEX = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$"
private const val USERNAME_VALIDATION_REGEX = "^[a-zA-Z0-9]+$"

class UsernameState(val username: String? = null) :
    TextFieldState(validator = ::isUsernameValid, errorFor = ::usernameValidationError) {
    init {
        username?.let {
            text = it
        }
    }
}

private fun usernameValidationError(username: String): String {
    return "Invalid username: $username （a-z，A-Z，0-9）and  $username > 4 size"
}

private fun isUsernameValid(username: String): Boolean {
    return Pattern.matches(USERNAME_VALIDATION_REGEX, username) && username.length > 4
}

val UsernameStateSaver = textFieldStateSaver(UsernameState())
