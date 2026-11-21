package com.example.smartquiz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {

    @Insert
    suspend fun insertResult(result: QuizResult)

    @Query(
        "SELECT * FROM quiz_results ORDER BY completedAt DESC, id DESC"
    )
    fun getAllResults(): Flow<List<QuizResult>>

    @Query("SELECT COUNT(*) FROM quiz_results")
    fun getTotalQuizzes(): Flow<Int>

    @Query("DELETE FROM quiz_results")
    suspend fun deleteAllResults()
}
