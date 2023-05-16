package com.mvince.compose.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mvince.compose.network.QuestionsOfTheDayApi
import com.mvince.compose.network.model.Result
import javax.inject.Inject

class QuestionsApiRepository @Inject constructor(
    private val api: QuestionsOfTheDayApi
) {

    suspend fun getQuestionsOfDay(): List<Result> {
        val response = api.getQuestions()

        return response.results
    }
}