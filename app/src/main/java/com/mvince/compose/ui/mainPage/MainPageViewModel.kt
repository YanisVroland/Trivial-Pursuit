package com.mvince.compose.ui.mainPage

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mvince.compose.repository.QuestionsRepository
import com.mvince.compose.network.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(private val questionsRepository: QuestionsRepository): ViewModel() {

    val SCORE_INCREMENT = 10

    var answered = false

    var _questions: List<Result> = listOf()
    var currentIndex = 0

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
            _questions = questionsRepository.getQuestionsOfDay()
            _question.value = _questions.first()
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
        currentIndex ++
        if(currentIndex < _questions.size){
            _question.value = _questions[currentIndex]
        }
        _isCorrect.value = null
        answered = false
    }
}