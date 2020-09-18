package com.example.nattramn.features.user.data

import android.app.Application
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.ui.UserView

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
            val userArticles = profileRemoteDataSource.getUserArticles(username)
            if (userArticles.status == Status.SUCCESS) {
                val articleViews = userArticles.data?.articleNetworks?.map { articleNetwork ->
                    articleNetwork.toArticleView(Resource.success(null))
                }
                responseArticles = Resource.success(articleViews)
            }
        }

        return responseArticles
    }

    suspend fun getBookmarkedArticles(username: String): Resource<List<ArticleView>> {
        var responseArticles = Resource<List<ArticleView>>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val bookmarkedArticles = profileRemoteDataSource.getBookmarkedArticles(username)
            if (bookmarkedArticles.status == Status.SUCCESS) {
                val articleViews = bookmarkedArticles.data?.articleNetworks?.map { articleNetwork ->
                    articleNetwork.toArticleView(Resource.success(null))
                }
                responseArticles = Resource.success(articleViews)
            }
        }

        return responseArticles
    }

    suspend fun getProfile(username: String): Resource<UserView> {

        var responseArticles = Resource<UserView>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val profile = profileRemoteDataSource.getProfile(username)
            if (profile.status == Status.SUCCESS) {
                val userView = profile.data?.userNetwork?.toUserView()
                responseArticles = Resource.success(userView)
            }
        }

        return responseArticles

    }

    suspend fun deleteArticle(slug: String): Resource<Unit> {
        var response = Resource<Unit>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = profileRemoteDataSource.deleteArticle(slug)
            if (request.status == Status.SUCCESS) {
                response = Resource.success(request.data)
            }
        }
        return response
    }

}