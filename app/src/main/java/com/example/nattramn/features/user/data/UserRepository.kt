package com.example.nattramn.features.user.data

import android.util.Log
import com.example.nattramn.core.RestClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository private constructor() {

    companion object {

        private const val TAG = "jalil"
        private var myInstance: UserRepository? = null

        fun getInstance(): UserRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = UserRepository()
                }
            }
            return myInstance!!
        }
    }

    suspend fun loginUser() {
        GlobalScope.launch {

            val loginResponse = safeApiCall {
                RestClient.getInstance().getApiService().loginUser(
                    LoginRequest(
                        UserNetwork(
                            username = "username",
                            email = "email@sample.com",
                            password = "password"
                        )
                    )
                )
            }

            loginResponse?.let { response ->

                withContext(Dispatchers.Main) {
                    Log.i(TAG, response.userNetwork.username.toString())
                }

            } ?: run {
                withContext(Dispatchers.Main) {
                    Log.i(TAG, "Process Failed to complete.")
                }
            }

        }
    }

    private suspend inline fun <T> safeApiCall(responseFunction: () -> T): T? {
        return try {
            responseFunction.invoke()//Or responseFunction()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
