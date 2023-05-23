package com.mvince.compose.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.mvince.compose.domain.UserFirebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserFirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    private var dailyScore: Int = 0
    private var totalScore: Int = 0
    private var isPlaying: Boolean = false

    fun getDailyScore() = dailyScore

    fun setDailyScore(newScore: Int) {
        this.dailyScore = newScore
    }

    fun getIsPlaying() = isPlaying

    fun setIsPlaying(playing: Boolean) {
        isPlaying = playing
    }

    fun insertUser(id:String, user: UserFirebase) : Boolean {
         return firestore.collection(_collection).document(id).set(user).isSuccessful
    }

    fun updateUserDailyScore(id:String, dailyScore : Int) : Boolean {
        this.dailyScore = dailyScore
        this.totalScore += dailyScore
        val date = LocalDate.now().toString()
        return firestore.collection(_collection).document(id).update(mapOf("dailyScore" to dailyScore,"lastGameDate" to date,"totalScore" to this.totalScore)).isSuccessful
    }

    suspend fun getUser(id: String): UserFirebase? {
        val user =  firestore.collection(_collection).document(id).snapshots().first().toObject<UserFirebase>()
        this.totalScore = user!!.totalScore
        return user
    }

    fun updateUser(id: String, updatedUser: UserFirebase) : Boolean{
        return firestore.collection(_collection).document(id).update(mapOf("pseudo" to updatedUser.pseudo,"totalScore" to updatedUser.totalScore,"avatar" to updatedUser.avatar)).isSuccessful
    }

    fun getAllSortedByTotalScore(): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).orderBy("totalScore", Query.Direction.DESCENDING).snapshots().map { it.toObjects<UserFirebase>() }
    }

    fun getAllSortedByDailyScore(): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).orderBy("dailyScore", Query.Direction.DESCENDING).snapshots().map { it.toObjects<UserFirebase>() }
    }

    companion object {
        private const val _collection: String = "USER"
    }
}