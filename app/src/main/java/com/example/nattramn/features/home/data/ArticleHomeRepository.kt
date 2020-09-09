package com.example.nattramn.features.home.data

import android.app.Application
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.RemoteDataSource
import com.example.nattramn.features.article.ui.ArticleView
import java.util.*

class ArticleHomeRepository(
    private val remoteDataSource: RemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {
        private const val TAG = "jalil"
        private var myInstance: ArticleHomeRepository? = null

        fun getInstance(application: Application): ArticleHomeRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance =
                        ArticleHomeRepository(RemoteDataSource(), LocalDataSource(application))
                }
            }
            return myInstance!!
        }
    }

    suspend fun getArticles(): ArrayList<ArticleView> {
        return localDataSource.getArticles()
    }

}