package com.example.nattramn.features.user.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user")
    val userNetwork: UserNetwork
)