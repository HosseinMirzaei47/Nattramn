package com.example.nattramn.core

import android.app.Application
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView

class LocalDataSource(private val application: Application) {

    suspend fun getArticles(): ArrayList<ArticleView> {
        return Utils(application).initArticles()
    }

    suspend fun getComments(): ArrayList<CommentView> {
        return Utils(application).initComments()
    }

}