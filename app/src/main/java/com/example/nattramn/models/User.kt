package com.example.nattramn.models

data class User(
    val name: String,
    val job: String,
    val image: String,
    val followers: Int,
    val favoriteArticles: ArrayList<Article>,
    val publishedArticle: ArrayList<Article>,
    val isMe: Boolean
)