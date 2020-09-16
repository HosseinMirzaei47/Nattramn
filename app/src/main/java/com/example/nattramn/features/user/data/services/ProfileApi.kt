package com.example.nattramn.features.user.data.services

import com.example.nattramn.features.home.data.ArticlesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {

    @GET("articles")
    suspend fun getUserArticles(@Query("author") username: String): ArticlesListResponse

}