package com.example.nattramn.features.user.data

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("user")
    val userNetwork: UserNetwork
)
