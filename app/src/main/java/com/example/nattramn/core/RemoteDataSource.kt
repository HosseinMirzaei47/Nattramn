package com.example.nattramn.core

import com.example.nattramn.features.user.data.models.AuthRequest
import com.example.nattramn.features.user.data.services.AuthApi

class RemoteDataSource {

    suspend fun login(request: AuthRequest) = safeApiCall {
        ServiceBuilder.buildService(AuthApi::class.java).loginUser(request)
    }

    suspend fun register(request: AuthRequest) = safeApiCall {
        ServiceBuilder.buildService(AuthApi::class.java).registerUser(request)
    }

}