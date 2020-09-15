package com.example.nattramn.features.home.data


import com.google.gson.annotations.SerializedName

data class FeedResponse(
    @SerializedName("articles")
    val articleNetworks: List<ArticleNetwork>,
    @SerializedName("articlesCount")
    val articlesCount: Int
)