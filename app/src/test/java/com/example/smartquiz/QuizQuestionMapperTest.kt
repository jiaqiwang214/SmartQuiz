package com.example.smartquiz

import com.example.smartquiz.data.repository.QuizQuestionMapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizQuestionMapperTest {

    @Test
    fun optionsContainFourAnswers() {

        val options = QuizQuestionMapper.buildOptions(
            correctAnswer = "Paris",
            incorrectAnswers = listOf(
                "London",
                "Berlin",
                "Rome"
            )
        )

        assertEquals(4, options.size)
    }

    @Test
    fun optionsContainCorrectAnswer() {

        val options = QuizQuestionMapper.buildOptions(
            correctAnswer = "Paris",
            incorrectAnswers = listOf(
                "London",
                "Berlin",
                "Rome"
            )
        )

        assertTrue(options.contains("Paris"))
    }

    @Test
    fun correctAnswerIndexIsValid() {

        val options = QuizQuestionMapper.buildOptions(
            correctAnswer = "Paris",
            incorrectAnswers = listOf(
                "London",
                "Berlin",
                "Rome"
            )
        )

        val index = QuizQuestionMapper.findCorrectAnswerIndex(
            options = options,
            correctAnswer = "Paris"
        )

        assertTrue(index in 0..3)
    }

    @Test
    fun correctAnswerIndexPointsToCorrectOption() {

        val options = QuizQuestionMapper.buildOptions(
            correctAnswer = "Paris",
            incorrectAnswers = listOf(
                "London",
                "Berlin",
                "Rome"
            )
        )

        val index = QuizQuestionMapper.findCorrectAnswerIndex(
            options = options,
            correctAnswer = "Paris"
        )

        assertEquals("Paris", options[index])
    }

    @Test
    fun shuffledOptionsPreserveAllAnswers() {

        val options = QuizQuestionMapper.buildOptions(
            correctAnswer = "Paris",
            incorrectAnswers = listOf(
                "London",
                "Berlin",
                "Rome"
            )
        )

        assertEquals(
            setOf("Paris", "London", "Berlin", "Rome"),
            options.toSet()
        )
    }

    @Test
    fun invalidIncorrectAnswerCountThrowsException() {

        var exceptionThrown = false

        try {
            QuizQuestionMapper.buildOptions(
                correctAnswer = "Paris",
                incorrectAnswers = listOf("London")
            )
        } catch (e: IllegalArgumentException) {
            exceptionThrown = true
        }

        assertTrue(exceptionThrown)
    }
}
