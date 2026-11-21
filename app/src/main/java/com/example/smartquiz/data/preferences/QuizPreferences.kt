package com.example.smartquiz.data.preferences

import android.content.Context

class QuizPreferences(context: Context) {

    private val preferences = context.applicationContext
        .getSharedPreferences(
            "smart_quiz_preferences",
            Context.MODE_PRIVATE
        )

    fun getDifficulty(): String {
        return preferences.getString(
            "quiz_difficulty",
            "medium"
        ) ?: "medium"
    }

    fun setDifficulty(difficulty: String) {
        require(difficulty in listOf("easy", "medium", "hard"))

        preferences.edit()
            .putString("quiz_difficulty", difficulty)
            .apply()
    }
}
