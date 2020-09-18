package com.example.nattramn.features.home.data

import com.example.nattramn.features.home.data.models.AllTagsResponse
import com.example.nattramn.features.home.data.models.ArticlesListResponse
import retrofit2.http.GET

interface HomeApi {

    @GET("articles/feed")
    suspend fun getFeedArticles(): ArticlesListResponse

    @GET("articles")
    suspend fun getAllArticles(): ArticlesListResponse

    @GET("tags")
    suspend fun getAllTags(): AllTagsResponse

}