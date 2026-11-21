package com.example.smartquiz.util

object QuizScoreCalculator {

    fun calculateScore(
        currentScore: Int,
        selectedAnswer: String,
        correctAnswer: String
    ): Int {
        return if (selectedAnswer == correctAnswer) {
            currentScore + 1
        } else {
            currentScore
        }
    }

    fun calculateAccuracy(
        score: Int,
        totalQuestions: Int
    ): Double {
        if (totalQuestions <= 0) return 0.0

        return score.toDouble() / totalQuestions * 100
    }
}
