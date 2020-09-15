package com.example.nattramn.features.home.data

import android.app.Application
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.features.article.ui.ArticleView
import java.util.*

class ArticleHomeRepository(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {
        private var myInstance: ArticleHomeRepository? = null

        fun getInstance(application: Application): ArticleHomeRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance =
                        ArticleHomeRepository(HomeRemoteDataSource(), LocalDataSource(application))
                }
            }
            return myInstance!!
        }
    }

    suspend fun getFeedArticles(): Resource<FeedResponse> {
        var response: Resource<FeedResponse> = Resource<FeedResponse>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            response = homeRemoteDataSource.getFeedArticles()
        }

        return response
    }

    suspend fun getArticles(): ArrayList<ArticleView> {
        return localDataSource.getArticles()
    }

}