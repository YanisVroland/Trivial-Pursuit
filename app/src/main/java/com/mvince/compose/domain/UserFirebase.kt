package com.mvince.compose.domain

import com.mvince.compose.R

data class UserFirebase(
    var pseudo: String,
    var email: String,
    var score: Int = 0,
    var avatar: Int = R.drawable.gamepad_variant
)
