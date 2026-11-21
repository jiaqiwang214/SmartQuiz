package com.example.smartquiz.data.repository

import com.example.smartquiz.data.local.QuizResult
import com.example.smartquiz.data.local.QuizResultDao
import kotlinx.coroutines.flow.Flow

class QuizResultRepository(
    private val dao: QuizResultDao
) {

    val allResults: Flow<List<QuizResult>> =
        dao.getAllResults()

    suspend fun saveResult(
        score: Int,
        totalQuestions: Int
    ) {
        require(totalQuestions > 0)
        require(score in 0..totalQuestions)

        dao.insertResult(
            QuizResult(
                score = score,
                totalQuestions = totalQuestions
            )
        )
    }

    suspend fun clearResults() {
        dao.deleteAllResults()
    }
}
