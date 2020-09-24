package com.example.nattramn.features.home.data

import androidx.room.withTransaction
import com.example.nattramn.core.AppDatabase
import com.example.nattramn.core.MyApp
import com.example.nattramn.features.article.data.TagEntity
import com.example.nattramn.features.article.data.models.TagAndArticleEntity
import com.example.nattramn.features.home.data.models.ArticleNetwork
import com.example.nattramn.features.user.data.UserEntity

class ArticleHomeLocalDataSource {
    private val db = AppDatabase.buildDatabase(MyApp.app)

    fun getAllArticles() = db.articleDao().getAllArticles()

    fun getArticleComments(slug: String) = db.commentDao().getArticleComments(slug)

    fun getAllTags() = db.tagDao().getAllTags()

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

}