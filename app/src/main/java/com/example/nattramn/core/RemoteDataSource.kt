package com.example.nattramn.core

import com.example.nattramn.features.user.data.LoginRequest
import com.example.nattramn.features.user.data.services.AuthApi

class RemoteDataSource {

    suspend fun login(request: LoginRequest) = safeApiCall {
        ServiceBuilder.buildService(AuthApi::class.java).loginUser(request)
    }

}