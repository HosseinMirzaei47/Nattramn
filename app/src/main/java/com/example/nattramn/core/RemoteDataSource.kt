package com.example.nattramn.core

import com.example.nattramn.features.user.data.LoginRequest

class RemoteDataSource {

    suspend fun login(request: LoginRequest) = safeApiCall {
        ServiceBuilder.buildService(MyApis::class.java).loginUser(request)
    }

}