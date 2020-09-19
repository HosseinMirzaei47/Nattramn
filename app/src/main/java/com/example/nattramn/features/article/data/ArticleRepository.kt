package com.example.nattramn.features.article.data

import android.app.Application
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.features.article.data.models.ArticleComments
import com.example.nattramn.features.article.data.models.CommentRequest
import com.example.nattramn.features.article.data.models.EditArticleRequest
import com.example.nattramn.features.article.ui.ArticleView

class ArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {
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
                response = Resource.success(request.data?.article?.toArticleEntity())
            }
        }

        return response
    }

    suspend fun getArticleComments(slug: String): Resource<ArticleComments> {
        var response = Resource<ArticleComments>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = articleRemoteDataSource.getArticleComments(slug)
            if (request.status == Status.SUCCESS) {
                response = Resource.success(request.data)
            } else if (request.status == Status.ERROR) {
                response = Resource.error("Something went wrong", request.data)
            }
        }

        return response
    }

    suspend fun getSingleArticle(slug: String): Resource<ArticleView> {
        var response = Resource<ArticleView>(Status.ERROR, null, null)
        var tagsEntity: List<TagEntity>?
        var commentsEntity: List<CommentEntity>?

        if (NetworkHelper.isOnline(MyApp.app)) {
            val articlesRequest = articleRemoteDataSource.getSingleArticle(slug)
            val commentsRequest = articleRemoteDataSource.getArticleComments(slug)

            if (articlesRequest.status == Status.SUCCESS) {
                tagsEntity = articlesRequest.data?.article?.tagList?.map { tag ->
                    TagEntity.convertTag(slug, tag)
                }
            }

            if (commentsRequest.status == Status.SUCCESS) {
                val commentsEntity = commentsRequest.data?.comments?.map { comment ->
                    CommentEntity.convertComment(
                        username = comment.author.username,
                        commentBody = comment.body,
                        image = comment.author.image,
                        ownerSlug = slug
                    )
                }
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

    suspend fun getTagArticles(tag: String): Resource<List<ArticleView>> {
        var responseArticles = Resource<List<ArticleView>>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val tagArticles = articleRemoteDataSource.getTagArticles(tag)
            if (tagArticles.status == Status.SUCCESS) {
                val articleViews = tagArticles.data?.articleNetworks?.map {
                    it.toArticleEntity()
                }
                responseArticles = Resource.success(articleViews)
            }
        }

        return responseArticles
    }

    suspend fun editArticle(editArticleRequest: EditArticleRequest): Resource<ArticleView> {
        var response = Resource<ArticleView>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = articleRemoteDataSource.editArticle(editArticleRequest)
            when (request.status) {
                Status.SUCCESS -> {
                    response =
                        Resource.success(request.data?.article?.toArticleEntity())
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