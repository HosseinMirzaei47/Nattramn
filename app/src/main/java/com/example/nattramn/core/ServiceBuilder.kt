package com.example.nattramn.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val client = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.5.69:3000/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

/*
class RestClient private constructor() {

    companion object {

        private const val BASE_URL = "http://192.168.5.69:3000/api/"
        private lateinit var myApis: MyApis
        private var mInstance: RestClient? = null

        fun getInstance(): RestClient {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = RestClient()
                }
            }
            return mInstance!!
        }

    }

    init {

        val client = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        myApis = retrofit.create(myApis::class.java)

    }

    fun getApiService() = myApis
}*/
