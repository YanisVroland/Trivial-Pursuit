package com.mvince.compose.ui.mainPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mvince.compose.repository.QuestionsApiRepository
import com.mvince.compose.network.model.Result
import com.mvince.compose.repository.AuthFirebaseRepository
import com.mvince.compose.repository.QuestionsFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainPageViewModel @Inject constructor(private val questionsFirebaseRepository: QuestionsFirebaseRepository, private val questionsAPIRepository: QuestionsApiRepository, private val repository: AuthFirebaseRepository) : ViewModel() {

    val SCORE_INCREMENT = 10

    var answered = false

    var _questions: List<Result> = listOf()
    var currentIndex = 0

    private val _currentUser = MutableStateFlow<FirebaseUser?>(repository.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    private val _question = MutableStateFlow<Result?>(null)
    val question: StateFlow<Result?>
    get() = _question

    private val _isCorrect = MutableStateFlow<Boolean?>(null)
    val isCorrect: StateFlow<Boolean?>
    get() = _isCorrect

    private val _totalScore = MutableStateFlow<Int>(0)
    val totalScore: StateFlow<Int>
        get() = _totalScore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            questionsFirebaseRepository.getDailyQuestions().collect() {
                if (it != null) {
                    _questions = it.questionsWithAnswers
                }else {
                    _questions = questionsAPIRepository.getQuestionsOfDay()
                    questionsFirebaseRepository.importDailyQuestions(_questions)
                }
            }
        }
    }

    fun validateAnswer(answer: String){
        _isCorrect.update { answer.equals(question.value?.correctAnswer) }
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
            }
            _isCorrect.value = null
            answered = false
        }
    }

    fun logout() {
        repository.logout();
    }


}