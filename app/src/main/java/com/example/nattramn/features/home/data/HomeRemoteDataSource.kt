package com.example.nattramn.features.home.data

import com.example.nattramn.core.ServiceBuilder
import com.example.nattramn.core.safeApiCall

class HomeRemoteDataSource {

    suspend fun getFeedArticles() = safeApiCall {
        ServiceBuilder.buildService(HomeApi::class.java).getFeedArticles()
    }

    suspend fun getAllArticles() = safeApiCall {
        ServiceBuilder.buildService(HomeApi::class.java).getAllArticles()
    }

}