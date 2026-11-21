package com.example.smartquiz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.QuizDatabase
import com.example.smartquiz.data.local.QuizResult
import com.example.smartquiz.data.repository.QuizResultRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuizResultViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = QuizResultRepository(
        QuizDatabase.getDatabase(application).quizResultDao()
    )

    val allResults: StateFlow<List<QuizResult>> =
        repository.allResults.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun saveResult(
        score: Int,
        totalQuestions: Int
    ) {
        viewModelScope.launch {
            repository.saveResult(
                score = score,
                totalQuestions = totalQuestions
            )
        }
    }

    fun clearResults() {
        viewModelScope.launch {
            repository.clearResults()
        }
    }
}
