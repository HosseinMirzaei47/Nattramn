package com.example.nattramn.features.home.data

import com.example.nattramn.features.article.data.models.EditArticleRequest
import com.example.nattramn.features.home.data.models.AllTagsResponse
import com.example.nattramn.features.home.data.models.ArticlesListResponse
import com.example.nattramn.features.home.data.models.CreateArticleRequest
import com.example.nattramn.features.home.data.models.SingleArticleResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface HomeApi {

    @GET("articles/feed")
    suspend fun getFeedArticles(): ArticlesListResponse

    @GET("articles")
    suspend fun getAllArticles(): ArticlesListResponse

    @GET("tags")
    suspend fun getAllTags(): AllTagsResponse

    @POST("articles")
    suspend fun createArticle(@Body createArticleRequest: CreateArticleRequest): SingleArticleResponse

    @PUT("articles/{slug}")
    suspend fun editArticle(
        @Body editArticleRequest: EditArticleRequest,
        slug: String
    ): SingleArticleResponse

}