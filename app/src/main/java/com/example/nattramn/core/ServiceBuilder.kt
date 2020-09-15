package com.example.nattramn.core

import androidx.lifecycle.MutableLiveData
import com.example.nattramn.core.MyApp.Companion.networkFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {

    val responseStatus = MutableLiveData<Response>()
    private val localDataSource = LocalDataSource(MyApp.app)
    var logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val proceed = chain.proceed(request = chain.request())
                responseStatus.postValue(proceed)
                return proceed
            }
        })
        .addInterceptor { chain ->
            var newBuilder = chain.request().newBuilder()
            localDataSource.getToken()?.let { token ->
                newBuilder = newBuilder.addHeader("Authorization", "Token $token")
            }
            val request = newBuilder.build()
            chain.proceed(request)
        }
        .addNetworkInterceptor(logging)
        .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
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
