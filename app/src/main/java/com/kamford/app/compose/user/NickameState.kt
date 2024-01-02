package com.kamford.app.compose.user

import java.util.regex.Pattern

private const val NICKNAME_VALIDATION_REGEX = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$"

class NicknameState(nickname: String? = null) :
    TextFieldState(validator = ::isNicknameValid, errorFor = ::nicknameValidationError) {
    init {
        nickname?.let {
            text = it
        }
    }
}

private fun nicknameValidationError(nickname: String): String {
    return "Invalid username: $nickname （a-z，A-Z，0-9，中文/簡繁）and  $nickname > 1 size"
}

private fun isNicknameValid(nickname: String): Boolean {
    return Pattern.matches(NICKNAME_VALIDATION_REGEX, nickname) && nickname.length > 1
}

val NicknameStateSaver = textFieldStateSaver(NicknameState())
