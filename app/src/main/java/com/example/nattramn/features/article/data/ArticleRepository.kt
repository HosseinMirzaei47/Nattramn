package com.example.nattramn.features.article.data

import android.app.Application
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import com.example.nattramn.features.user.data.AuthRemoteDataSource

class ArticleRepository(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {

        private const val TAG = "jalil"
        private var myInstance: ArticleRepository? = null

        fun getInstance(application: Application): ArticleRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = ArticleRepository(
                        AuthRemoteDataSource(), LocalDataSource(
                            application
                        )
                    )
                }
            }
            return myInstance!!
        }
    }

    suspend fun getArticles(): ArrayList<ArticleView> {
        return localDataSource.getArticles()
    }

    suspend fun getComments(): ArrayList<CommentView> {
        return localDataSource.getComments()
    }

}