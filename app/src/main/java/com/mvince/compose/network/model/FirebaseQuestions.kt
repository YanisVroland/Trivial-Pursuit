package com.mvince.compose.network.model


data class FirebaseQuestions(
    val questionsWithAnswers: List<Result>
) {

    constructor() : this(emptyList())
}
