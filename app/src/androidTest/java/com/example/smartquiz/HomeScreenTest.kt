
package com.example.smartquiz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreenDisplaysAllButtons() {
        composeTestRule.setContent {
            HomeScreen(
                onStartQuiz = {},
                onStatistics = {},
                onSettings = {}
            )
        }

        composeTestRule
            .onNodeWithText("Start Quiz")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("My Statistics")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Settings")
            .assertIsDisplayed()
    }

    @Test
    fun startQuizButtonWorks() {
        var clicked = false

        composeTestRule.setContent {
            HomeScreen(
                onStartQuiz = { clicked = true },
                onStatistics = {},
                onSettings = {}
            )
        }

        composeTestRule
            .onNodeWithText("Start Quiz")
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(true, clicked)
        }
    }

    @Test
    fun statisticsButtonWorks() {
        var clicked = false

        composeTestRule.setContent {
            HomeScreen(
                onStartQuiz = {},
                onStatistics = { clicked = true },
                onSettings = {}
            )
        }

        composeTestRule
            .onNodeWithText("My Statistics")
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(true, clicked)
        }
    }

    @Test
    fun settingsButtonWorks() {
        var clicked = false

        composeTestRule.setContent {
            HomeScreen(
                onStartQuiz = {},
                onStatistics = {},
                onSettings = { clicked = true }
            )
        }

        composeTestRule
            .onNodeWithText("Settings")
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(true, clicked)
        }
    }
}
