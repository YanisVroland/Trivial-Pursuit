package com.mvince.compose.ui.mainPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.QuestionsApiRepository
import com.mvince.compose.network.model.Result
import com.mvince.compose.repository.AuthFirebaseRepository
import com.mvince.compose.repository.QuestionsFirebaseRepository
import com.mvince.compose.repository.UserFirebaseRepository
import com.mvince.compose.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainPageViewModel @Inject constructor(private val questionsFirebaseRepository: QuestionsFirebaseRepository, private val questionsAPIRepository: QuestionsApiRepository, private val repository: AuthFirebaseRepository, private val userFirebaseRepository: UserFirebaseRepository) : ViewModel() {

    private val SCORE_INCREMENT = 10

    var answered = false

    private val cardColors = listOf(pinkTrivial, purpleTrivial, greenTrivial, blueTrivial, yellowTrivial, orangeTrivial)
    var currentCardColor: Color = Color.LightGray

    var _questions: List<Result> = listOf()
    var currentIndex = 0

    val allUsers: StateFlow<List<UserFirebase?>> = userFirebaseRepository.getAllSortedByTotalScore().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allUsersDaily: StateFlow<List<UserFirebase?>> = userFirebaseRepository.getAllSortedByDailyScore().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _user = MutableStateFlow(UserFirebase())
    val user: StateFlow<UserFirebase>
    get() = _user

    private val _question = MutableStateFlow<Result?>(null)
    val question: StateFlow<Result?>
    get() = _question

    private val _answers = MutableStateFlow(listOf<String>())
    val answers: StateFlow<List<String>>
    get() = _answers

    private val _isCorrect = MutableStateFlow<Boolean?>(null)
    val isCorrect: StateFlow<Boolean?>
    get() = _isCorrect

    private val _updateIsCorrect = MutableStateFlow<Boolean?>(null)
    val updateIsCorrect: StateFlow<Boolean?>
        get() = _updateIsCorrect

    private val _totalScore = MutableStateFlow(0)
    val totalScore: StateFlow<Int>
        get() = _totalScore

    private val _finalScore = MutableStateFlow(userFirebaseRepository.getDailyScore())
    val finalScore: StateFlow<Int>
    get() = _finalScore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            questionsFirebaseRepository.getDailyQuestions().collect {
                if (it != null) {
                    _questions = it.questionsWithAnswers
                }else {
                    _questions = questionsAPIRepository.getQuestionsOfDay()
                    questionsFirebaseRepository.importDailyQuestions(_questions)
                }
                _question.value = _questions.first()
                _answers.value =
                    question.value?.incorrectAnswers?.plus(question.value!!.correctAnswer)?.shuffled()!!
                currentCardColor = cardColors.shuffled().first()
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val userId = repository.currentUser?.uid
            val user = userId?.let { userFirebaseRepository.getUser(it) }
            if (user != null) {
                _user.value = user
            }
        }
    }

    fun validateAnswer(answer: String){
        _isCorrect.update { answer == question.value?.correctAnswer }
        if(_isCorrect.value == true) {
            _totalScore.value += SCORE_INCREMENT
        }
        answered = true
    }

    fun nextQuestion() {
        if(answered) {
            currentIndex ++
            if(currentIndex < _questions.size){
                _question.value = _questions[currentIndex]
                _answers.value =
                    question.value?.incorrectAnswers?.plus(question.value!!.correctAnswer)?.shuffled()!!
                currentCardColor = cardColors.shuffled().first()
            }
            _isCorrect.value = null
            answered = false
        }
    }

    fun updateUserScore() {
        viewModelScope.launch(Dispatchers.IO) {
            userFirebaseRepository.setDailyScore(_totalScore.value)
            userFirebaseRepository.updateUserDailyScore(repository.currentUser!!.uid, _totalScore.value)
        }
      }

    fun logout() {
        repository.logout()
    }

    fun updateUser(user: UserFirebase) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = repository.currentUser?.uid
            if (userId != null) {
                _user.value.avatar = user.avatar
                _updateIsCorrect.value = userFirebaseRepository.updateUser(userId,user)
            }
        }
    }

    fun stopToastUpdate(){
        _updateIsCorrect.value = null
    }

    fun stopToastQuestion(){
        _isCorrect.value = null
    }

}