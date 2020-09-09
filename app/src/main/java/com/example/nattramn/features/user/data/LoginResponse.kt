package com.example.nattramn.features.user.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user")
    val userNetwork: UserNetwork
)
