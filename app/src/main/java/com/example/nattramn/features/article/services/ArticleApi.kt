package com.example.nattramn.features.article.services

import com.example.nattramn.features.home.data.SingleArticleResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ArticleApi {

    @GET("articles/{slug}")
    suspend fun getSingleArticle(@Path("slug") slug: String): SingleArticleResponse

    @POST("articles/{slug}/favorite")
    suspend fun bookmarkArticle(@Path("slug") slug: String): SingleArticleResponse

}