package com.example.nattramn.features.user.data

import com.google.gson.annotations.SerializedName

data class UserNetwork(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("token")
    val token: String? = null
)