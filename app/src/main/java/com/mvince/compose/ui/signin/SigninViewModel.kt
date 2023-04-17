package com.mvince.compose.ui.signin

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
class SigninViewModel  @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _signinFlow = MutableStateFlow<FirebaseUser?>(null)
    val signinFlow: StateFlow<FirebaseUser?> = _signinFlow

    fun loginUser(email: String, password: String) {
        viewModelScope.launch { _signinFlow.value = repository.login(email, password) }
    }

}