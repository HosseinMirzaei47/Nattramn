package com.example.nattramn.models

data class Article(
    val user: User,
    val date: String,
    val title: String,
    val body: String,
    val tags: ArrayList<Tag>,
    val comments: ArrayList<Comment>,
    val suggestions: ArrayList<Suggestions>
)