package com.example.nattramn.features.home.data


import com.google.gson.annotations.SerializedName

data class FeedResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("articlesCount")
    val articlesCount: Int
)