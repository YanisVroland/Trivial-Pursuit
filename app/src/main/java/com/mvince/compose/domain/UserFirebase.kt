package com.mvince.compose.domain

import com.mvince.compose.R

data class UserFirebase(
    var pseudo: String,
    var email: String,
    var totalScore: Int = 0,
    var avatar: Int = 2
){
    constructor() : this("Unknow", "Unknow@gmail.com", 0, 2)
}
