package com.example.mad_test.data.remote

import com.example.mad_test.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GNewsApi {
    @GET("api/v4/top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String = "general",
        @Query("lang") lang: String = "en",
        @Query("country") country: String,
        @Query("max") max: Int = 10,
        @Query("apikey") apiKey: String
    ): NewsResponse
}
