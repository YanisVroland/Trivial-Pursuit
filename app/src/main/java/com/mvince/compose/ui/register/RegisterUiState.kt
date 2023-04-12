package com.mvince.compose.ui.register

data class RegisterUiState(
    var email: String = "",
    var password: String = "",
    var password2: String = "",
    var showPassword: Boolean = false,
    var showPassword2: Boolean = false,
    val offline: Boolean = false
)

