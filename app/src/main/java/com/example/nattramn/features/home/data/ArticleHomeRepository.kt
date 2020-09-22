package com.example.nattramn.features.home.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.features.article.data.models.EditArticleRequest
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.home.data.models.AllTagsResponse
import com.example.nattramn.features.home.data.models.CreateArticleRequest

class ArticleHomeRepository(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {
        private var myInstance: ArticleHomeRepository? = null

        fun getInstance(): ArticleHomeRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance =
                        ArticleHomeRepository(HomeRemoteDataSource(), LocalDataSource())
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

    fun getAllArticlesDb(): LiveData<MutableList<ArticleView>> {

        val articlesView = mutableListOf<ArticleView>()
        val articlesEntity = localDataSource.getAllArticles()
        return articlesEntity.map { list ->
            list.forEach {
                it.comments = localDataSource.getArticleComments(it.slug)
                it.tags = localDataSource.getArticleTags(it.slug)
                val user = localDataSource.getUser(it.ownerUsername).value?.toUserView()

                articlesView.add(
                    ArticleView(
                        userView = user!!,
                        date = it.date,
                        title = it.title,
                        body = it.body,
                        tags = it.tags.map { it.tag },
                        commentViews = it.comments.map { comment -> comment.toCommentView() },
                        likes = it.favoriteCount.toString(),
                        commentsNumber = it.comments.size,
                        bookmarked = it.bookmarked,
                        slug = it.slug

                    )
                )
            }
            articlesView
        }

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

    suspend fun createArticle(createArticleRequest: CreateArticleRequest): Resource<ArticleView> {
        var resource = Resource<ArticleView>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = homeRemoteDataSource.createArticle(createArticleRequest)
            if (request.status == Status.SUCCESS) {
                val articleView = request.data?.article?.toArticleView(Resource.success(null))
                resource = Resource.success(articleView)
            }
        }
        return resource
    }

    suspend fun editArticle(
        editArticleRequest: EditArticleRequest, slug: String
    ): Resource<ArticleView> {
        var response = Resource<ArticleView>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = homeRemoteDataSource.editArticle(editArticleRequest, slug)
            when (request.status) {
                Status.SUCCESS -> {
                    response =
                        Resource.success(request.data?.article?.toArticleView(Resource.success(null)))
                }
                Status.LOADING -> {
                    Resource.error("Loading", null)
                }
                Status.ERROR -> {
                    Resource.error("An error happened", null)
                }
            }
        }

        return response
    }

}