package com.example.smartquiz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class StatisticsScreenTest {

    @get:Rule
    val composeTestRule =
        createAndroidComposeRule<MainActivity>()

    private fun openStatistics() {
        composeTestRule
            .onNodeWithText("My Statistics")
            .performClick()

        composeTestRule
            .onNodeWithText("Total Quizzes:", substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun statisticsPageDisplaysCorrectly() {
        openStatistics()

        composeTestRule
            .onNodeWithText("My Statistics")
            .assertIsDisplayed()
    }

    @Test
    fun totalQuizzesIsDisplayed() {
        openStatistics()

        composeTestRule
            .onNodeWithText("Total Quizzes:", substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun averageAccuracyIsDisplayed() {
        openStatistics()

        composeTestRule
            .onNodeWithText("Average Accuracy:", substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun bestScoreIsDisplayed() {
        openStatistics()

        composeTestRule
            .onNodeWithText("Best Score:", substring = true)
            .assertIsDisplayed()
    }


    @Test
    fun backToHomeWorks() {
        openStatistics()

        composeTestRule
            .onNodeWithText("Back to Home")
            .performClick()

        composeTestRule
            .onNodeWithText("Start Quiz")
            .assertIsDisplayed()
    }

}
