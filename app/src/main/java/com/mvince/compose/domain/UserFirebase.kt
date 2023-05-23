package com.mvince.compose.domain


data class UserFirebase(
    var pseudo: String,
    var email: String,
    var totalScore: Int = 0,
    var dailyScore: Int = 0,
    var avatar: Int = 1
){
    constructor() : this("Unknow", "Unknow@gmail.com", 0,0, 1)

    constructor(pseudo: String, totalScore: Int, avatar: Int) : this(pseudo, "", totalScore, 0, avatar)
}
