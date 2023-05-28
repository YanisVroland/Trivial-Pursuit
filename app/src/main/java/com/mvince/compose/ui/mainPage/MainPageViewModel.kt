package com.mvince.compose.ui.mainPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.AuthFirebaseRepository
import com.mvince.compose.repository.UserFirebaseRepository
import com.mvince.compose.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val repository: AuthFirebaseRepository,
    private val userFirebaseRepository: UserFirebaseRepository
) : ViewModel() {

    val allUsers: StateFlow<List<UserFirebase?>> = userFirebaseRepository.getAllSortedByTotalScore()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allUsersDaily: StateFlow<List<UserFirebase?>> =
        userFirebaseRepository.getAllSortedByDailyScore()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _user = MutableStateFlow(UserFirebase())
    val user: StateFlow<UserFirebase>
        get() = _user

    private val _updateIsCorrect = MutableStateFlow<Boolean?>(null)
    val updateIsCorrect: StateFlow<Boolean?>
        get() = _updateIsCorrect


    private val _finalScore = MutableStateFlow(userFirebaseRepository.getDailyScore())
    val finalScore: StateFlow<Int>
        get() = _finalScore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = repository.currentUser?.uid
            val user = userId?.let { userFirebaseRepository.getUser(it) }
            if (user != null) {
                _user.value = user
            }
        }
    }
    fun updateUser(user: UserFirebase) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = repository.currentUser?.uid
            if (userId != null) {
                _user.value.avatar = user.avatar
                _updateIsCorrect.value = userFirebaseRepository.updateUser(userId, user)
            }
        }
    }

    fun stopToastUpdate() {
        _updateIsCorrect.value = null
    }

    fun logout() {
        repository.logout()
    }
}