package com.example.smartquiz.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApiService {

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 5,
        @Query("type") type: String = "multiple",
        @Query("difficulty") difficulty: String? = null
    ): TriviaResponse
}
