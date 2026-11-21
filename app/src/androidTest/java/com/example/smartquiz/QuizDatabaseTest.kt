package com.example.smartquiz

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.content.Context
import com.example.smartquiz.data.local.QuizDatabase
import com.example.smartquiz.data.local.QuizResult
import com.example.smartquiz.data.local.QuizResultDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizDatabaseTest {

    private lateinit var database: QuizDatabase
    private lateinit var dao: QuizResultDao

    @Before
    fun setup() {
        val context = ApplicationProvider
            .getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            QuizDatabase::class.java
        ).build()

        dao = database.quizResultDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertResultSuccessfully() = runBlocking {
        dao.insertResult(
            QuizResult(
                score = 4,
                totalQuestions = 5
            )
        )

        val results = dao.getAllResults().first()

        assertEquals(1, results.size)
    }

    @Test
    fun readSavedResultCorrectly() = runBlocking {
        dao.insertResult(
            QuizResult(
                score = 3,
                totalQuestions = 5
            )
        )

        val result = dao.getAllResults().first().first()

        assertEquals(3, result.score)
        assertEquals(5, result.totalQuestions)
    }

    @Test
    fun multipleResultsAreSaved() = runBlocking {
        dao.insertResult(
            QuizResult(score = 4, totalQuestions = 5)
        )

        dao.insertResult(
            QuizResult(score = 2, totalQuestions = 5)
        )

        dao.insertResult(
            QuizResult(score = 5, totalQuestions = 5)
        )

        val results = dao.getAllResults().first()

        assertEquals(3, results.size)
    }

    @Test
    fun totalQuizCountIsCorrect() = runBlocking {
        dao.insertResult(
            QuizResult(score = 4, totalQuestions = 5)
        )

        dao.insertResult(
            QuizResult(score = 3, totalQuestions = 5)
        )

        val total = dao.getTotalQuizzes().first()

        assertEquals(2, total)
    }

    @Test
    fun deleteAllResultsWorks() = runBlocking {
        dao.insertResult(
            QuizResult(score = 4, totalQuestions = 5)
        )

        dao.insertResult(
            QuizResult(score = 3, totalQuestions = 5)
        )

        dao.deleteAllResults()

        val results = dao.getAllResults().first()

        assertTrue(results.isEmpty())
    }

    @Test
    fun newDatabaseIsEmpty() = runBlocking {
        val results = dao.getAllResults().first()

        assertEquals(0, results.size)
    }
}
