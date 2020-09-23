package com.example.nattramn.features.user.data

import com.example.nattramn.features.user.ui.UserView
import com.google.gson.annotations.SerializedName

data class UserNetwork(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("bio")
    val bio: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("following")
    val following: Boolean? = false,
    @SerializedName("image")
    val image: String? = null
) {
    fun toUserView(): UserView {
        return UserView(
            name = username!!,
            job = "مدرس زبان انگلیسی",
            image = image!!,
            followers = "85",
            following = following
        )
    }
}