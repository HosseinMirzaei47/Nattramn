package com.example.nattramn.features.article.data

import com.example.nattramn.core.ServiceBuilder
import com.example.nattramn.core.safeApiCall
import com.example.nattramn.features.article.services.ArticleApi

class ArticleRemoteDataSource {

    suspend fun getSingleArticle(slug: String) = safeApiCall {
        ServiceBuilder.buildService(ArticleApi::class.java).getSingleArticle(slug)
    }

    suspend fun bookmarkArticle(slug: String) =
        safeApiCall {
            ServiceBuilder.buildService(ArticleApi::class.java).bookmarkArticle(slug)
        }

}