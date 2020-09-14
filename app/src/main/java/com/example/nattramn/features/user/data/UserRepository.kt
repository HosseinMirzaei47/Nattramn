package com.example.nattramn.features.user.data

import android.app.Application
import android.content.Context
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.RemoteDataSource
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {

        private var myInstance: UserRepository? = null
        fun getInstance(application: Application): UserRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = UserRepository(
                        RemoteDataSource(),
                        LocalDataSource(application)
                    )
                }
            }
            return myInstance!!
        }
    }

    suspend fun loginUser(context: Context, user: LoginRequest): Resource<LoginResponse> {

        var response: Resource<LoginResponse> = Resource<LoginResponse>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(context)) {
            response = remoteDataSource.login(user)

            if (response.status == Status.SUCCESS) {
                val token =
                    response.data?.userNetwork?.token ?: return Resource.error(
                        "Invalid token",
                        response.data
                    )
                localDataSource.saveToken(token)
            }
        }

        return response
    }

}
