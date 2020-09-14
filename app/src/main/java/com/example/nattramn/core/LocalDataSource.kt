package com.example.nattramn.core

import android.app.Application
import com.example.nattramn.core.storage.data.PreferenceProperty.Companion.getPreferences
import com.example.nattramn.core.storage.data.Settings
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView

class LocalDataSource(private val application: Application) {

    private val db = AppDatabase.buildDatabase(application, Utils(application).MIGRATION_1_2)
    private val settings = Settings(application.getPreferences())

    fun saveToken(token: String) {
        settings.authToken = token
    }

    fun getToken() = settings.authToken

    suspend fun getArticles(): ArrayList<ArticleView> {
        return Utils(application).initArticles()
    }

    suspend fun getComments(): ArrayList<CommentView> {
        return Utils(application).initComments()
    }

}