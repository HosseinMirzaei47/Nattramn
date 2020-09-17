package com.example.nattramn.features.article.data.models


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("following")
    val following: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("username")
    val username: String
)