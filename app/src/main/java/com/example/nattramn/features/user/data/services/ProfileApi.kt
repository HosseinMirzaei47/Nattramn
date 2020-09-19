package com.example.nattramn.features.user.data.services

import com.example.nattramn.features.home.data.models.ArticlesListResponse
import com.example.nattramn.features.user.data.models.UserProfileResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApi {

    @GET("articles")
    suspend fun getUserArticles(@Query("author") username: String): ArticlesListResponse

    @GET("articles")
    suspend fun getBookmarkedArticles(@Query("favorited") username: String): ArticlesListResponse

    @GET("profiles/{username}")
    suspend fun getProfile(@Path("username") username: String): UserProfileResponse

    @DELETE("articles/{slug}")
    suspend fun deleteArticle(@Path("slug") slug: String): Response<Unit>?

}