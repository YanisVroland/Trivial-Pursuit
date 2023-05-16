package com.mvince.compose.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.mvince.compose.repository.AuthFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel @Inject  constructor(private val repository: AuthFirebaseRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<Boolean>(repository.currentUser != null)
    val currentUser: StateFlow<Boolean> = _currentUser

}