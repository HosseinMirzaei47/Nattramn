package com.example.nattramn.core

import com.example.nattramn.core.resource.Resource
import retrofit2.HttpException

suspend inline fun <T> safeApiCall(responseFunction: suspend () -> T): Resource<T> {
    return try {
        Resource.success(responseFunction.invoke())
    } catch (e: HttpException) {
        Resource.error(e.code().toString(), null)
    }
}