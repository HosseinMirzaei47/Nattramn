package com.example.nattramn.features.home.data

import com.example.nattramn.core.ServiceBuilder
import com.example.nattramn.core.safeApiCall
import com.example.nattramn.features.article.services.ArticleApi

class HomeRemoteDataSource {

    suspend fun getFeedArticles() = safeApiCall {
        ServiceBuilder.buildService(HomeApi::class.java).getFeedArticles()
    }

    suspend fun getSingleArticle(slug: String) = safeApiCall {
        ServiceBuilder.buildService(ArticleApi::class.java).getSingleArticle(slug)
    }

    suspend fun getArticleComments(slug: String) = safeApiCall {
        ServiceBuilder.buildService(HomeApi::class.java).getArticleComments(slug)
    }

}