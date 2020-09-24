package com.example.nattramn.core

import com.example.nattramn.core.storage.data.PreferenceProperty.Companion.getPreferences
import com.example.nattramn.core.storage.data.Settings

class AuthLocalDataSource {

    private val settings = Settings(MyApp.app.getPreferences())
    private val db = AppDatabase.buildDatabase(MyApp.app)

    fun saveToken(token: String) {
        settings.authToken = token
    }

    fun saveUsername(username: String) {
        settings.authUsername = username
    }

    fun getToken() = settings.authToken

    fun getUsername() = settings.authUsername

}