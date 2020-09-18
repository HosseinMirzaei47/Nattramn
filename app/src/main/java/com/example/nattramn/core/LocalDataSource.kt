package com.example.nattramn.core

import android.app.Application
import com.example.nattramn.core.storage.data.PreferenceProperty.Companion.getPreferences
import com.example.nattramn.core.storage.data.Settings

class LocalDataSource(private val application: Application) {

    private val settings = Settings(application.getPreferences())

    fun saveToken(token: String) {
        settings.authToken = token
    }

    fun saveUsername(username: String) {
        settings.authUsername = username
    }

    fun getToken() = settings.authToken

    fun getUsername() = settings.authUsername

}