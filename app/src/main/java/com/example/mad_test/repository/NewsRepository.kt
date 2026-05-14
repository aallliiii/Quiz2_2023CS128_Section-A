package com.example.mad_test.repository

import com.example.mad_test.BuildConfig
import com.example.mad_test.data.model.Article
import com.example.mad_test.data.remote.GNewsApi
import com.example.mad_test.data.remote.RetrofitClient

class NewsRepository(
    private val api: GNewsApi = RetrofitClient.api,
    private val apiKey: String = BuildConfig.GNEWS_API_KEY
) {
    suspend fun getTopHeadlines(countryCode: String): List<Article> {
        val response = api.getTopHeadlines(country = countryCode, apiKey = apiKey)
        return response.articles
    }
}
