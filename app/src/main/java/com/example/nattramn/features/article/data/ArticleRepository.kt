package com.example.nattramn.features.article.data

import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.toArticleView
import com.example.nattramn.features.article.data.models.ArticleComments
import com.example.nattramn.features.article.data.models.CommentRequest
import com.example.nattramn.features.article.data.models.EditArticleRequest
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.UserEntity

class ArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {
        private var myInstance: ArticleRepository? = null

        fun getInstance(): ArticleRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = ArticleRepository(
                        ArticleRemoteDataSource(), LocalDataSource()
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
        val articleEntity: ArticleEntity?
        val userEntity: UserEntity?
        val tagsEntity: List<TagEntity>?
        var commentsEntity: List<CommentEntity>? = listOf()

        if (NetworkHelper.isOnline(MyApp.app)) {
            val articleRequest = articleRemoteDataSource.getSingleArticle(slug)
            when (articleRequest.status) {
                Status.SUCCESS -> {
                    articleEntity = articleRequest.data?.article?.toArticleEntity()
                    userEntity = articleRequest.data?.article?.author?.convertUser()
                    tagsEntity = articleRequest.data?.article?.tagList?.map { tag ->
                        TagEntity.convertTag(slug, tag)
                    }
                    val commentsRequest = articleRemoteDataSource.getArticleComments(slug)
                    if (commentsRequest.status == Status.SUCCESS) {
                        commentsEntity = commentsRequest.data?.comments?.map { comment ->
                            CommentEntity.convertComment(
                                comment.author.username,
                                comment.body,
                                comment.author.image,
                                slug
                            )
                        }
                    }

                    localDataSource.insertUser(userEntity!!)
                    localDataSource.insertArticle(articleEntity!!)
                    localDataSource.insertAllComments(commentsEntity!!)
                    localDataSource.insertAllTags(tagsEntity!!)

                    val articleView =
                        toArticleView(userEntity, articleEntity, tagsEntity, commentsEntity)

                    response = Resource.success(articleView)
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
                    it.toArticleView(Resource.success(null))
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