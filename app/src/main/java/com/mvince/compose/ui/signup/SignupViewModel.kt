package com.mvince.compose.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mvince.compose.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _signupFlow = MutableStateFlow<FirebaseUser?>(null)
    val signupFlow: StateFlow<FirebaseUser?> = _signupFlow

    fun signupUser(email: String, password: String) {
        viewModelScope.launch { _signupFlow.value = repository.signup(email, password) }
    }
}