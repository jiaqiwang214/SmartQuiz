package com.example.smartquiz.data.repository

import com.example.smartquiz.data.remote.RetrofitClient
import com.example.smartquiz.data.remote.TriviaApiService
import com.example.smartquiz.data.remote.TriviaQuestion
import java.util.Locale

data class QuizItem(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

class QuizRepository(
    private val apiService: TriviaApiService = RetrofitClient.apiService
) {

    suspend fun getQuestions(
        amount: Int = 5,
        difficulty: String? = null
    ): List<QuizItem> {

        require(amount in 1..50) {
            "Question amount must be between 1 and 50."
        }

        val selectedDifficulty = difficulty?.lowercase(Locale.ROOT)

        require(
            selectedDifficulty == null ||
                    selectedDifficulty in listOf("easy", "medium", "hard")
        ) {
            "Invalid difficulty."
        }

        val response = apiService.getQuestions(
            amount = amount,
            difficulty = selectedDifficulty
        )

        if (response.responseCode != 0) {
            throw IllegalStateException(
                "Quiz API returned error code: ${response.responseCode}"
            )
        }

        if (response.results.isEmpty()) {
            throw IllegalStateException("No questions available.")
        }

        return response.results.map { convertQuestion(it) }
    }

    private fun convertQuestion(question: TriviaQuestion): QuizItem {

        val correctAnswer = decodeHtml(question.correctAnswer)

        val incorrectAnswers = question.incorrectAnswers.map {
            decodeHtml(it)
        }


        val allOptions = QuizQuestionMapper.buildOptions(
            correctAnswer = correctAnswer,
            incorrectAnswers = incorrectAnswers
        )

        val correctIndex = QuizQuestionMapper.findCorrectAnswerIndex(
            options = allOptions,
            correctAnswer = correctAnswer
        )

        return QuizItem(
            question = decodeHtml(question.question),
            options = allOptions,
            correctAnswerIndex = correctIndex
        )

    }


    private fun decodeHtml(value: String): String {
        return HtmlDecoder.decode(value)
    }
}
