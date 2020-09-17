package com.example.nattramn.features.article.data.models


import com.google.gson.annotations.SerializedName

data class CommentList(
    @SerializedName("author")
    val author: Author,
    @SerializedName("body")
    val body: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String
)