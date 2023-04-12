package com.mvince.compose.ui.login

data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var showPassword: Boolean = false,
    val offline: Boolean = false
)