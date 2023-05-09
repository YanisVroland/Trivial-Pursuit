package com.mvince.compose.ui.mainPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.compose.repository.QuestionsRepository
import com.mvince.compose.network.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(private val questionsRepository: QuestionsRepository): ViewModel() {

    private val _currentQuestion = MutableStateFlow<Result?>(null)
        val currentQuestion: StateFlow<Result?>
        get() = _currentQuestion

    private val _questions = flow{
        val questions = questionsRepository.getQuestionsOfDay()
        emit(questions)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val question: StateFlow<List<Result>>
    get() = _questions

    fun validateAnswers(){
        //GESTION DU SCORE
        _currentQuestion.update {
            _questions.value.get(1)
        }
    }
}