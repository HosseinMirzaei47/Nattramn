package com.example.nattramn.core

import com.example.nattramn.core.resource.Resource

suspend inline fun <T> safeApiCall(responseFunction: suspend () -> T): Resource<T> {
    return try {
        Resource.success(responseFunction.invoke())
    } catch (e: Exception) {
        Resource.error(e.message.orEmpty(), null)
    }
}