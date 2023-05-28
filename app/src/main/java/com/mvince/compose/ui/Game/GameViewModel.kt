package com.mvince.compose.ui.Game

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.compose.network.model.Result
import com.mvince.compose.repository.AuthFirebaseRepository
import com.mvince.compose.repository.QuestionsRepository
import com.mvince.compose.repository.UserFirebaseRepository
import com.mvince.compose.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class GameViewModel @Inject constructor(private val questionsRepository: QuestionsRepository, private val repository: AuthFirebaseRepository, private val userFirebaseRepository: UserFirebaseRepository) : ViewModel() {
    private val SCORE_INCREMENT = 10
    var answered = false
    var currentIndex = 0

    private val darkCardColors = listOf<Color>(dpinkTrivial, dpurpleTrivial, dgreenTrivial, dblueTrivial, dyellowTrivial, dorangeTrivial)
    var darkCurrentCardColor: Color = Color.DarkGray
    private val cardColors = listOf(pinkTrivial, purpleTrivial, greenTrivial, blueTrivial, yellowTrivial, orangeTrivial)
    var currentCardColor: Color = Color.LightGray

    var _questions: List<Result> = listOf()

    private val _question = MutableStateFlow<Result?>(null)
    val question: StateFlow<Result?>
    get() = _question

    private val _answers = MutableStateFlow(listOf<String>())
    val answers: StateFlow<List<String>>
    get() = _answers

    private val _isCorrect = MutableStateFlow<Boolean?>(null)
    val isCorrect: StateFlow<Boolean?>
    get() = _isCorrect

    private val _totalScore = MutableStateFlow(0)
    val totalScore: StateFlow<Int>
        get() = _totalScore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            questionsRepository.getDailyQuestions().collect {
                if (it != null) {
                    _questions = it.questionsWithAnswers
                }else {
                    _questions = questionsRepository.getQuestionsOfDay()
                    questionsRepository.importDailyQuestions(_questions)
                }
                _question.value = _questions.first()
                _answers.value =
                    question.value?.incorrectAnswers?.plus(question.value!!.correctAnswer)?.shuffled()!!
                currentCardColor = cardColors.shuffled().first()
                darkCurrentCardColor = darkCardColors.shuffled().first()
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
                darkCurrentCardColor = darkCardColors.shuffled().first()
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

    fun stopToastQuestion(){
        _isCorrect.value = null
    }

}