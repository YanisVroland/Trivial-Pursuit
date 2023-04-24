package com.mvince.compose.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.AuthRepository
import com.mvince.compose.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val firebaseRepository: UserFirebaseRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signupFlow = MutableStateFlow<FirebaseUser?>(null)
    val signupFlow: StateFlow<FirebaseUser?> = _signupFlow

    private val _isAuthentificated = MutableStateFlow<Boolean>(false)
    val isAuthentificated: StateFlow<Boolean>
        get() = _isAuthentificated

    fun signupUser(email: String, password: String,pseudo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = authRepository.signup(email, password)?.uid
            if (uid != null) {
                _isAuthentificated.value = firebaseRepository.insertUser(uid, UserFirebase(pseudo,email));
            }
        }
    }
}