package com.example.mad_test.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsResponse(
    @SerializedName("totalArticles") val totalArticles: Int = 0,
    @SerializedName("articles") val articles: List<Article> = emptyList()
)

data class Article(
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null,
    @SerializedName("source") val source: Source? = null
) : Serializable

data class Source(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
) : Serializable
