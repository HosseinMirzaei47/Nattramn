package com.example.nattramn.features.article.data.models

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("body")
    val body: String
)