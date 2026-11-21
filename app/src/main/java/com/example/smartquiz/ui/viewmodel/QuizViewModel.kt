package com.example.smartquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.repository.QuizItem
import com.example.smartquiz.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.smartquiz.data.preferences.QuizPreferences

sealed interface QuizUiState {
    data object Idle : QuizUiState
    data object Loading : QuizUiState

    data class Success(
        val questions: List<QuizItem>
    ) : QuizUiState

    data class Error(
        val message: String
    ) : QuizUiState
}



class QuizViewModel(
    application: Application,
    private val repository: QuizRepository,
    private val quizPreferences: QuizPreferences
) : AndroidViewModel(application) {

    constructor(application: Application) : this(
        application = application,
        repository = QuizRepository(),
        quizPreferences = QuizPreferences(application)
    )



    private val _uiState =
        MutableStateFlow<QuizUiState>(QuizUiState.Idle)

    val uiState: StateFlow<QuizUiState> =
        _uiState.asStateFlow()

    init {
        loadQuestions()
    }

    fun loadQuestions(
        amount: Int = 5,
        difficulty: String? = null
    ) {
        viewModelScope.launch {

            _uiState.value = QuizUiState.Loading

            try {

                val selectedDifficulty =
                    difficulty ?: quizPreferences.getDifficulty()

                val questions = repository.getQuestions(
                    amount = amount,
                    difficulty = selectedDifficulty
                )


                _uiState.value = QuizUiState.Success(
                    questions = questions
                )

            } catch (e: IOException) {

                _uiState.value = QuizUiState.Error(
                    message = "Network error. Please check your internet connection."
                )

            } catch (e: Exception) {

                _uiState.value = QuizUiState.Error(
                    message = e.message ?: "Failed to load questions."
                )
            }
        }
    }
}
