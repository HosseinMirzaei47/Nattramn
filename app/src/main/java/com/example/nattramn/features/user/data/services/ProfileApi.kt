package com.example.nattramn.features.user.data.services

import com.example.nattramn.features.home.data.ArticlesListResponse
import com.example.nattramn.features.user.data.models.UserProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApi {

    @GET("articles")
    suspend fun getUserArticles(@Query("author") username: String): ArticlesListResponse

    @GET("profiles/{username}")
    suspend fun getProfile(@Path("username") username: String): UserProfileResponse

}