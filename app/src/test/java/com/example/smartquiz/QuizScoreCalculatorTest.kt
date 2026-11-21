package com.example.smartquiz

import com.example.smartquiz.util.QuizScoreCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class QuizScoreCalculatorTest {

    @Test
    fun correctAnswerIncreasesScore() {
        val result = QuizScoreCalculator.calculateScore(
            currentScore = 2,
            selectedAnswer = "Paris",
            correctAnswer = "Paris"
        )

        assertEquals(3, result)
    }

    @Test
    fun wrongAnswerDoesNotIncreaseScore() {
        val result = QuizScoreCalculator.calculateScore(
            currentScore = 2,
            selectedAnswer = "London",
            correctAnswer = "Paris"
        )

        assertEquals(2, result)
    }

    @Test
    fun accuracyIsCalculatedCorrectly() {
        val result = QuizScoreCalculator.calculateAccuracy(
            score = 4,
            totalQuestions = 5
        )

        assertEquals(80.0, result, 0.001)
    }

    @Test
    fun zeroQuestionsReturnsZeroAccuracy() {
        val result = QuizScoreCalculator.calculateAccuracy(
            score = 0,
            totalQuestions = 0
        )

        assertEquals(0.0, result, 0.001)
    }

    @Test
    fun perfectScoreReturnsOneHundredPercent() {
        val result = QuizScoreCalculator.calculateAccuracy(
            score = 5,
            totalQuestions = 5
        )

        assertEquals(100.0, result, 0.001)
    }
}
