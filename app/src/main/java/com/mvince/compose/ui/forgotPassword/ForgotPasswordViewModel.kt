package com.mvince.compose.ui.forgotPassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mvince.compose.ui.users.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())

    val uiState: StateFlow<ForgotPasswordUiState>
        get() = _uiState

}