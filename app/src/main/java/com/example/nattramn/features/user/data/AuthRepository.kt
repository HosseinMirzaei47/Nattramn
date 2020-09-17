package com.example.nattramn.features.user.data

import android.app.Application
import android.content.Context
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.features.user.data.models.AuthRequest
import com.example.nattramn.features.user.data.models.AuthResponse

class AuthRepository(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {

        private var myInstance: AuthRepository? = null
        fun getInstance(application: Application): AuthRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = AuthRepository(
                        AuthRemoteDataSource(),
                        LocalDataSource(application)
                    )
                }
            }
            return myInstance!!
        }
    }

    suspend fun loginUser(context: Context, user: AuthRequest): Resource<AuthResponse> {

        var response: Resource<AuthResponse> = Resource<AuthResponse>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(context)) {
            response = authRemoteDataSource.login(user)

            if (response.status == Status.SUCCESS) {
                val token =
                    response.data?.userNetwork?.token ?: return Resource.error(
                        "Authentication failed",
                        response.data
                    )
                val username =
                    response.data?.userNetwork?.username ?: return Resource.error(
                        "Authentication failed",
                        response.data
                    )
                localDataSource.saveToken(token)
                localDataSource.saveUsername(username)
            }
        }

        return response
    }

    suspend fun registerUser(context: Context, user: AuthRequest): Resource<AuthResponse> {
        var response: Resource<AuthResponse> = Resource<AuthResponse>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(context)) {
            response = authRemoteDataSource.register(user)
        }

        return response
    }

}
