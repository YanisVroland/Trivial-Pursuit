package com.mvince.compose.domain

data class UserFirebase(
    var pseudo: String = "",
    var email: String = "",
    var totalScore: Int = 0,
    var avatar: Int = 1,
    var dailyScore: Int = 0,
    var lastGameDate: String = ""
){
    constructor() : this("Unknow", "Unknow@gmail.com")

    constructor(pseudo: String, totalScore: Int, avatar: Int) : this(pseudo, "", totalScore, avatar)
}
