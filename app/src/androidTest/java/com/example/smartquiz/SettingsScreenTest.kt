package com.example.smartquiz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasClickAction
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

    @get:Rule
    val composeTestRule =
        createAndroidComposeRule<MainActivity>()

    private fun openSettings() {
        composeTestRule
            .onNodeWithText("Settings")
            .performClick()

        composeTestRule
            .onNodeWithText("Quiz Difficulty")
            .assertIsDisplayed()
    }

    @Test
    fun settingsPageDisplaysCorrectly() {
        openSettings()

        composeTestRule
            .onNodeWithText("Quiz History")
            .assertIsDisplayed()
    }

    @Test
    fun easyDifficultyCanBeSelected() {
        openSettings()

        composeTestRule
            .onNodeWithText("Easy", substring = true)
            .performClick()

        composeTestRule
            .onNodeWithText("Selected Difficulty: Easy")
            .assertIsDisplayed()
    }



    @Test
    fun mediumDifficultyCanBeSelected() {
        openSettings()

        composeTestRule
            .onNode(
                hasText("Medium", substring = true) and hasClickAction()
            )
            .performClick()

        composeTestRule
            .onNodeWithText("Selected Difficulty: Medium")
            .assertIsDisplayed()
    }



    @Test
    fun hardDifficultyCanBeSelected() {
        openSettings()

        composeTestRule
            .onNodeWithText("Hard", substring = true)
            .performClick()

        composeTestRule
            .onNodeWithText("Selected Difficulty: Hard")
            .assertIsDisplayed()
    }

    @Test
    fun clearHistoryShowsConfirmationDialog() {
        openSettings()

        composeTestRule
            .onNodeWithText("Clear Quiz History")
            .performClick()

        composeTestRule
            .onNodeWithText("Clear Quiz History?")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Delete All")
            .assertIsDisplayed()
    }

    @Test
    fun cancelClosesConfirmationDialog() {
        openSettings()

        composeTestRule
            .onNodeWithText("Clear Quiz History")
            .performClick()

        composeTestRule
            .onNodeWithText("Cancel")
            .performClick()

        composeTestRule
            .onNodeWithText("Clear Quiz History?")
            .assertDoesNotExist()
    }
}
