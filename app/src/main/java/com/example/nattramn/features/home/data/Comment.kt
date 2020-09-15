package com.example.nattramn.features.home.data

data class Comment(
    val author: Author,
    val body: String,
    val createdAt: String,
    val id: String
)