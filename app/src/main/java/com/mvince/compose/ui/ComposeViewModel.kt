package com.mvince.compose.ui

import androidx.lifecycle.ViewModel
import com.mvince.compose.repository.AuthFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel @Inject  constructor(repository: AuthFirebaseRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow(repository.currentUser != null)
    val currentUser: StateFlow<Boolean> = _currentUser

}