package com.example.nattramn.features.user.data

import com.example.nattramn.core.ServiceBuilder
import com.example.nattramn.core.safeApiCall
import com.example.nattramn.features.user.data.services.ProfileApi

class ProfileRemoteDataSource {

    suspend fun getUserArticles(username: String) =
        safeApiCall {
            ServiceBuilder.buildService(ProfileApi::class.java).getUserArticles(username)
        }

    suspend fun getBookmarkedArticles(username: String) =
        safeApiCall {
            ServiceBuilder.buildService(ProfileApi::class.java).getBookmarkedArticles(username)
        }

    suspend fun getProfile(username: String) = safeApiCall {
        ServiceBuilder.buildService(ProfileApi::class.java).getProfile(username)
    }

}