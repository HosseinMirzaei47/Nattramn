package com.example.nattramn.features.home.data

import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {

    @GET("articles/feed")
    suspend fun getFeedArticles(): ArticlesListResponse

    @GET("articles/{slug}/comments")
    suspend fun getArticleComments(@Path("slug") slug: String): ArticleComments

}