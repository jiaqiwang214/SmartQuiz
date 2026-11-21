package com.example.smartquiz.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val score: Int,

    val totalQuestions: Int,

    val completedAt: Long = System.currentTimeMillis()
)
