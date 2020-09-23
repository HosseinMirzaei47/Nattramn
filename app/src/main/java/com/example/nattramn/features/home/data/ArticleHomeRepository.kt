package com.example.nattramn.features.home.data

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

    fun getAllArticlesDb(): MutableList<ArticleView> {

        val articlesView = mutableListOf<ArticleView>()
        val articlesEntity = localDataSource.getAllArticles()

        articlesEntity.forEach { articleEntity ->
            articleEntity.comments = localDataSource.getArticleComments(articleEntity.slug)
            articleEntity.tags = localDataSource.getArticleTags(articleEntity.slug)
            val user = localDataSource.getUser(articleEntity.ownerUsername).toUserView()
            articlesView.add(
                ArticleView(
                    userView = user,
                    date = articleEntity.date,
                    title = articleEntity.title,
                    body = articleEntity.body,
                    tags = articleEntity.tags?.map { tag -> tag.tag },
                    commentViews = articleEntity.comments?.map { comment -> comment.toCommentView() },
                    likes = articleEntity.favoriteCount.toString(),
                    commentsNumber = articleEntity.comments?.size,
                    bookmarked = articleEntity.bookmarked,
                    slug = articleEntity.slug

                )
            )
        }

        return articlesView
    }

    suspend fun getAllArticles(): Resource<List<ArticleView>> {
        var responseArticles = Resource<List<ArticleView>>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val feedArticles = homeRemoteDataSource.getAllArticles()
            if (feedArticles.status == Status.SUCCESS) {
                localDataSource.updateAllArticles(feedArticles.data?.articleNetworks)
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