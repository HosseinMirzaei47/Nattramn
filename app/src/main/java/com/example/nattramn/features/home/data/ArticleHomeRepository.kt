package com.example.nattramn.features.home.data

import android.app.Application
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.home.data.models.AllTagsResponse

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

    suspend fun getFeedArticles(): Resource<List<ArticleView>> {
        var responseArticles = Resource<List<ArticleView>>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val feedArticles = homeRemoteDataSource.getFeedArticles()
            if (feedArticles.status == Status.SUCCESS) {
                val articleViews = feedArticles.data?.articleNetworks?.map {
                    it.toArticleView(Resource.success(null))
                }
                responseArticles = Resource.success(articleViews)
            }
        }

        return responseArticles
    }

    suspend fun getAllArticles(): Resource<List<ArticleView>> {
        var responseArticles = Resource<List<ArticleView>>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val feedArticles = homeRemoteDataSource.getAllArticles()
            if (feedArticles.status == Status.SUCCESS) {
                val articleViews = feedArticles.data?.articleNetworks?.map {
                    it.toArticleView(Resource.success(null))
                }
                responseArticles = Resource.success(articleViews)
            }
        }

        return responseArticles
    }

    suspend fun getAllTags(): Resource<AllTagsResponse> {
        var response = Resource<AllTagsResponse>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val allTags = homeRemoteDataSource.getAllTags()

            if (allTags.status == Status.SUCCESS) {
                response = Resource.success(allTags.data)
            }
        }

        return response
    }

}