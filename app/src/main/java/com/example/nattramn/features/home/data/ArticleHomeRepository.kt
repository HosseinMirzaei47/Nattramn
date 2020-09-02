package com.example.nattramn.features.home.data

import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.RemoteDataSource
import com.example.nattramn.features.article.ui.ArticleView
import java.util.*

class ArticleHomeRepository(
    private val remoteDataSource: RemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    suspend fun getArticles(): ArrayList<ArticleView> {
        return localDataSource.getArticles()
    }

}