package com.example.nattramn.features.user.data

import android.app.Application
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.features.article.ui.ArticleView

class ProfileRepository(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {

        private var myInstance: ProfileRepository? = null
        fun getInstance(application: Application): ProfileRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = ProfileRepository(
                        ProfileRemoteDataSource(),
                        LocalDataSource(application)
                    )
                }
            }
            return myInstance!!
        }
    }

    suspend fun getUserArticles(username: String): Resource<List<ArticleView>> {
        var responseArticles = Resource<List<ArticleView>>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val feedArticles = profileRemoteDataSource.getUserArticles(username)
            if (feedArticles.status == Status.SUCCESS) {
                val articleViews = feedArticles.data?.articleNetworks?.map {
                    it.toArticleView(Resource.success(null))
                }
                responseArticles = Resource.success(articleViews)
            }
        }

        return responseArticles
    }

}