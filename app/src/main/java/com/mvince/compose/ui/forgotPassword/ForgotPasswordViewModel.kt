package com.mvince.compose.ui.forgotPassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.mvince.compose.repository.AuthRepository
import com.mvince.compose.ui.users.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val repository: AuthRepository) :
    ViewModel() {
    private val _resetPasswordFlow = MutableStateFlow<Boolean?>(null)
    val resetPasswordFlow: StateFlow<Boolean?> = _resetPasswordFlow


    private val _errorFlow = MutableStateFlow<FirebaseAuthException?>(null)
    val errorFlow: MutableStateFlow<FirebaseAuthException?>
        get() = _errorFlow

    fun resetPassword(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
               _resetPasswordFlow.value = repository.resetPassword(email)
            } catch (e: FirebaseAuthException) {
                _errorFlow.value = e
                e.printStackTrace()
            }
        }
    }

}