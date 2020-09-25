package com.example.nattramn.core.storage.data

import android.content.SharedPreferences
import com.example.nattramn.core.storage.types.StringPreference

class Settings(sharedPreferences: SharedPreferences) {

    var authToken: String? by StringPreference(
        sharedPreferences,
        AUTH_TOKEN
    )

    var authUsername: String? by StringPreference(
        sharedPreferences,
        AUTH_USERNAME
    )

    companion object Key {
        const val AUTH_TOKEN = "AUTH_TOKEN"
        const val AUTH_USERNAME = "AUTH_USERNAME"
    }
}