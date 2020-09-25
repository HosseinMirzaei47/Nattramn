package com.example.nattramn.features.user.data

import androidx.room.withTransaction
import com.example.nattramn.core.config.MyApp
import com.example.nattramn.core.database.AppDatabase
import com.example.nattramn.features.article.data.TagEntity
import com.example.nattramn.features.article.data.models.TagAndArticleEntity
import com.example.nattramn.features.home.data.models.ArticleNetwork

class ProfileLocalDataSource {

    private val db = AppDatabase.buildDatabase(MyApp.app)

    fun getUser(username: String) = db.userDao().getUser(username)

    fun getUserArticles(username: String) = db.articleDao().getUserArticles(username)

    fun getArticleTags(slug: String) = db.tagDao().getArticleTags(slug)

    fun getArticleComments(slug: String) = db.commentDao().getArticleComments(slug)

    suspend fun insertUser(userNetwork: UserNetwork?) {
        db.withTransaction {
            db.userDao().insertUser(
                UserEntity(
                    userNetwork?.username!!,
                    userNetwork.following,
                    userNetwork.image!!
                )
            )
        }

    }

    suspend fun updateUser(userNetwork: UserNetwork?) {
        db.withTransaction {
            db.userDao().updateUser(
                UserEntity(
                    userNetwork?.username!!,
                    userNetwork.following,
                    userNetwork.image!!
                )
            )
        }
    }

    suspend fun updateUserArticles(articleNetworkList: List<ArticleNetwork>?) {
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
                    it.toArticleEntity(it.user.following)
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

}