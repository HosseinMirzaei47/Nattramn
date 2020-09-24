package com.example.nattramn.features.user.data.models

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("profile")
    val profile: Profile
)