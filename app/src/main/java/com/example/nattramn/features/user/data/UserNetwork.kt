package com.example.nattramn.features.user.data

import com.google.gson.annotations.SerializedName

data class UserNetwork(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("token")
    val token: String? = null
)