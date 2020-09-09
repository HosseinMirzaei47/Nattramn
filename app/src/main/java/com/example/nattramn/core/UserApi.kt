package com.example.nattramn.core

import com.example.nattramn.features.user.data.LoginRequest
import com.example.nattramn.features.user.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("users/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse

}