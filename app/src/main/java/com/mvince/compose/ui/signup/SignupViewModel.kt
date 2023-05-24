package com.mvince.compose.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.AuthFirebaseRepository
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
    private val authRepository: AuthFirebaseRepository
) : ViewModel() {

    private val _isAuthentificated = MutableStateFlow<Boolean?>(null)
    val isAuthentificated: StateFlow<Boolean?>
        get() = _isAuthentificated

    private val _errorFlow = MutableStateFlow<FirebaseAuthException?>(null)
    val errorFlow: MutableStateFlow<FirebaseAuthException?>
        get() = _errorFlow

    fun signupUser(email: String, password: String, pseudo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uid = authRepository.signup(email, password)?.uid
                if (uid != null) {
                    _isAuthentificated.value = firebaseRepository.insertUser(uid, UserFirebase(pseudo, email));
                }
            } catch (e: FirebaseAuthException) {
                _errorFlow.value = e
                e.printStackTrace()
            }
        }

    }
}