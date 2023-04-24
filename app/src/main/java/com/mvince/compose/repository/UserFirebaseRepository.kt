package com.mvince.compose.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.mvince.compose.domain.UserFirebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserFirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    fun insertUser(id:String, user: UserFirebase) : Boolean {
        return firestore.collection(_collection).document(id).set(user).isSuccessful
    }
    
    fun getAll(): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).snapshots().map { it.toObjects<UserFirebase>() }
    }

    companion object {
        private const val _collection: String = "USER"
    }
}