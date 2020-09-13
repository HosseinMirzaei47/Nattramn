package com.example.nattramn.features.article.data

import com.google.gson.annotations.SerializedName

data class Article(
    val author: Author,
    val body: String,
    val createdAt: String,
    val description: String,
    @SerializedName("favorited")
    val isBookmarked: Boolean,
    val favoritesCount: Int,
    val slug: String,
    val tagList: List<String>,
    val title: String,
    val updatedAt: String
)