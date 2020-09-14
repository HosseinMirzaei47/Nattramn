package com.example.nattramn.features.user.data.services

import com.example.nattramn.features.user.data.LoginRequest
import com.example.nattramn.features.user.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("users/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse

}