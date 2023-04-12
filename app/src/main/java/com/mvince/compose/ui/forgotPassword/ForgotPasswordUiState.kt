package com.mvince.compose.ui.forgotPassword

data class ForgotPasswordUiState(
    var email: String = "",
    val offline: Boolean = false
)