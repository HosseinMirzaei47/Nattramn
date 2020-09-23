package com.example.nattramn.core

import androidx.room.withTransaction
import com.example.nattramn.core.storage.data.PreferenceProperty.Companion.getPreferences
import com.example.nattramn.core.storage.data.Settings
import com.example.nattramn.features.article.data.ArticleEntity
import com.example.nattramn.features.article.data.CommentEntity
import com.example.nattramn.features.article.data.TagEntity
import com.example.nattramn.features.article.data.models.TagAndArticleEntity
import com.example.nattramn.features.home.data.models.ArticleNetwork
import com.example.nattramn.features.user.data.UserEntity

class LocalDataSource() {

    private val settings = Settings(MyApp.app.getPreferences())
    private val db = AppDatabase.buildDatabase(MyApp.app)

    suspend fun insertArticle(articleEntity: ArticleEntity) {
        db.articleDao().insertArticle(articleEntity)
    }

    suspend fun insertUser(userEntity: UserEntity) {
        db.userDao().insertUser(userEntity)
    }

    fun insertComment(commentEntity: CommentEntity) {
        db.commentDao().insertComment(commentEntity)
    }

    suspend fun insertAllTag(tagEntityList: List<TagEntity>?) {
        db.tagDao().insertTag(tagEntityList)
    }

    suspend fun insertAllArticlesAndTags(articles: List<ArticleEntity>?) {
        if (articles != null) {
            for (article in articles) {
                insertArticle(article)
                db.tagDao().insertTag(article.tags)
            }
        }
    }

    fun insertAllComments(comments: List<CommentEntity>) {
        for (comment in comments) {
            insertComment(comment)
        }
    }

    fun saveToken(token: String) {
        settings.authToken = token
    }

    fun saveUsername(username: String) {
        settings.authUsername = username
    }

    fun getToken() = settings.authToken

    fun getUsername() = settings.authUsername

    fun getAllArticles() = db.articleDao().getAllArticles()

    fun getArticleComments(slug: String) = db.commentDao().getArticleComments(slug)

    suspend fun updateAllArticles(articleNetworkList: List<ArticleNetwork>?) {
        articleNetworkList?.let { networkList ->
            db.withTransaction {
                db.userDao().insertUser(networkList.map {
                    UserEntity(
                        it.user.username,
                        it.user.following,
                        it.user.image
                    )
                })
                db.articleDao().insertArticle(networkList.map {
                    it.toArticleEntity()
                })
                networkList.forEach {
                    db.tagDao().insertTag(it.tagList.map { tag ->
                        TagEntity(tag)
                    })
                }
                networkList.forEach { articleNetwork ->
                    articleNetwork.tagList.forEach { tag ->
                        db.tagArticleDao().insertTagAndArticle(
                            TagAndArticleEntity(
                                tag = tag,
                                slug = articleNetwork.slug
                            )
                        )
                    }
                }
            }
        }
    }

    fun getArticleTags(slug: String) = db.tagDao().getArticleTags(slug)

    fun getUser(username: String) = db.userDao().getUser(username)

    fun getArticle(slug: String) {
        db.articleDao().getArticle(slug)
    }

}