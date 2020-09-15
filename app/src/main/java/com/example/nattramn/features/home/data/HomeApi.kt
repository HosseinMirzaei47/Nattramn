package com.example.nattramn.features.home.data

import retrofit2.http.GET

interface HomeApi {

    @GET("articles/feed")
    suspend fun getFeedArticles(): FeedResponse

}