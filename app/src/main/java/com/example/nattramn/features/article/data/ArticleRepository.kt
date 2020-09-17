package com.example.nattramn.features.article.data

import android.app.Application
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.features.article.data.models.CommentRequest
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView

class ArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {

        private const val TAG = "jalil"
        private var myInstance: ArticleRepository? = null

        fun getInstance(application: Application): ArticleRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = ArticleRepository(
                        ArticleRemoteDataSource(), LocalDataSource(
                            application
                        )
                    )
                }
            }
            return myInstance!!
        }
    }

    suspend fun bookmarkArticle(slug: String): Resource<ArticleView> {
        var response = Resource<ArticleView>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = articleRemoteDataSource.bookmarkArticle(slug)
            if (request.status == Status.SUCCESS) {
                response =
                    Resource.success(request.data?.article?.toArticleView(Resource.success(null)))
            }
        }

        return response

    }

    suspend fun getSingleArticle(slug: String): Resource<ArticleView> {
        var response = Resource<ArticleView>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = articleRemoteDataSource.getSingleArticle(slug)
            if (request.status == Status.SUCCESS) {
                val articleView = request.data?.article?.toArticleView(Resource.success(null))
                response = Resource.success(articleView)
            } else if (request.status == Status.ERROR) {
                response = Resource.error("no slug", null)
            } else if (request.status == Status.LOADING) {
                response = Resource.loading(null)
            }
        }

        return response

    }

    suspend fun sendComment(slug: String, commentRequest: CommentRequest): Resource<Unit> {
        var response = Resource<Unit>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = articleRemoteDataSource.sendComment(slug, commentRequest)
            response = when (request.status) {
                Status.SUCCESS -> {
                    Resource.success(request.data)
                }
                Status.ERROR -> {
                    Resource.error("no slug", null)
                }
                Status.LOADING -> {
                    Resource.loading(null)
                }
            }
        }

        return response
    }

    suspend fun getArticles(): ArrayList<ArticleView> {
        return localDataSource.getArticles()
    }

    suspend fun getComments(): ArrayList<CommentView> {
        return localDataSource.getComments()
    }

}