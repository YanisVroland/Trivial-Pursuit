package com.mvince.compose.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.mvince.compose.network.model.FirebaseQuestions
import com.mvince.compose.util.formatDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import com.mvince.compose.network.model.Result

class QuestionsFirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun importDailyQuestions(questions: List<Result>): Boolean {
        val todaysDate = LocalDate.now().toString()

        val test = FirebaseQuestions(questions)

        return firestore
                .collection(_collection)
                .document(todaysDate)
                .set(test)
                .isSuccessful
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDailyQuestions(): Flow<FirebaseQuestions?> {
        val todaysDate = LocalDate.now().toString()
        return firestore.collection(_collection).document(todaysDate).snapshots()
            .map { it.toObject(FirebaseQuestions::class.java) }
    }

    companion object {
        private const val _collection: String = "DAILYQUESTIONS"
    }
}