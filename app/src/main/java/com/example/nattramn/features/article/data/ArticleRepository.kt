package com.example.nattramn.features.article.data

import com.example.nattramn.core.config.MyApp
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.utils.NetworkHelper
import com.example.nattramn.core.utils.toArticleView
import com.example.nattramn.features.article.data.models.CommentRequest
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import com.example.nattramn.features.user.data.UserEntity

class ArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private var localDataSource: ArticleLocalDataSource
) {

    companion object {
        private var myInstance: ArticleRepository? = null

        fun getInstance(): ArticleRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = ArticleRepository(
                        ArticleRemoteDataSource(), ArticleLocalDataSource()
                    )
                }
            }
            return myInstance!!
        }
    }

    fun getTagArticlesDb(tag: String) = articleEntityListToView(localDataSource.getTagArticles(tag))

    fun getSingleArticleDb(slug: String) = articleEntityToView(localDataSource.getArticle(slug))

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

    suspend fun getArticleComments(slug: String): Resource<List<CommentView>> {
        var response = Resource<List<CommentView>>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = articleRemoteDataSource.getArticleComments(slug)
            if (request.status == Status.SUCCESS) {

                val comments = request.data?.comments?.map {
                    it.toCommentView()
                }

                response = Resource.success(comments)
            } else if (request.status == Status.ERROR) {
                response = Resource.error("Something went wrong", null)
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
                    articleEntity =
                        articleRequest.data?.article?.toArticleEntity(articleRequest.data.article.user.following)
                    userEntity = articleRequest.data?.article?.user?.convertUser()
                    tagsEntity = articleRequest.data?.article?.tagList?.map { tag ->
                        TagEntity.convertTag(tag)
                    }
                    val commentsRequest = articleRemoteDataSource.getArticleComments(slug)
                    if (commentsRequest.status == Status.SUCCESS) {
                        commentsEntity = commentsRequest.data?.comments?.map { comment ->
                            CommentEntity.convertComment(
                                comment.id,
                                comment.user.username,
                                comment.body,
                                comment.user.image,
                                comment.createdAt,
                                slug
                            )
                        }
                    }

                    localDataSource.insertUser(userEntity!!)
                    localDataSource.insertArticle(articleEntity!!)
                    localDataSource.insertAllComments(commentsEntity!!)
                    localDataSource.insertAllTags(tagsEntity)

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

    /*          TYPE CONVERTERS          */
    private fun articleEntityListToView(articlesEntity: List<ArticleEntity>): MutableList<ArticleView> {
        val articlesView = mutableListOf<ArticleView>()

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

    private fun articleEntityToView(articleEntity: ArticleEntity): ArticleView {
        articleEntity.comments = localDataSource.getArticleComments(articleEntity.slug)
        articleEntity.tags = localDataSource.getArticleTags(articleEntity.slug)
        val user = localDataSource.getUser(articleEntity.ownerUsername).toUserView()
        return ArticleView(
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
    }
}