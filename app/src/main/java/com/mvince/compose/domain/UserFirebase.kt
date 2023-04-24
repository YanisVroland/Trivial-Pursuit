package com.mvince.compose.domain

data class UserFirebase(
    var pseudo: String,
    var email: String,
    var score: Int = 0
)
