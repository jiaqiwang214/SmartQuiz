package com.example.smartquiz.data.repository

object QuizQuestionMapper {

    fun buildOptions(
        correctAnswer: String,
        incorrectAnswers: List<String>
    ): List<String> {

        require(incorrectAnswers.size == 3) {
            "Exactly three incorrect answers are required"
        }

        return (incorrectAnswers + correctAnswer).shuffled()
    }

    fun findCorrectAnswerIndex(
        options: List<String>,
        correctAnswer: String
    ): Int {
        return options.indexOf(correctAnswer)
    }
}
