package com.example.nattramn.features.article.data

import androidx.room.withTransaction
import com.example.nattramn.core.config.MyApp
import com.example.nattramn.core.database.AppDatabase
import com.example.nattramn.core.storage.data.PreferenceProperty.Companion.getPreferences
import com.example.nattramn.core.storage.data.Settings
import com.example.nattramn.features.article.data.models.TagAndArticleEntity
import com.example.nattramn.features.home.data.models.ArticleNetwork
import com.example.nattramn.features.user.data.UserEntity

class ArticleLocalDataSource {

    private val settings = Settings(MyApp.app.getPreferences())
    private val db = AppDatabase.buildDatabase(MyApp.app)

    fun logout() {
        db.userDao().clearUserTable()
        db.articleDao().clearArticleTable()
        db.tagDao().clearTagsTable()
        db.commentDao().clearCommentsTable()
        db.tagArticleDao().clearTagArticlesTable()
    }

    suspend fun likeArticle(slug: String) = db.likesDao().insertLikedArticle(LikesEntity(slug))

    fun unlikeArticle(slug: String) = db.likesDao().unlikeArticle(LikesEntity(slug))

    suspend fun insertArticle(articleEntity: ArticleEntity?) {
        db.articleDao().insertArticle(articleEntity)
    }

    suspend fun insertArticle(articleEntity: List<ArticleNetwork>?) {
        articleEntity?.let { list ->
            db.withTransaction {
                db.articleDao().insertArticle(list.map { article ->
                    article.toArticleEntity(article.user.following)
                })
            }
        }
    }

    suspend fun insertAllArticlesAndTags(articles: List<ArticleEntity>?) {
        if (articles != null) {
            for (article in articles) {
                insertArticle(article)
                db.tagDao().insertTag(article.tags)
            }
        }
    }

    suspend fun insertUser(userEntity: UserEntity?) {
        db.userDao().insertUser(userEntity)
    }

    suspend fun insertUsers(articleNetworks: List<ArticleNetwork>?) {
        db.withTransaction {
            articleNetworks?.let { list ->
                db.userDao().insertUser(list.map { articleNetwork ->
                    UserEntity(
                        articleNetwork.user.username,
                        articleNetwork.user.following,
                        articleNetwork.user.image
                    )
                })
            }
        }
    }

    suspend fun insertAllTags(tagEntityList: List<TagEntity>?) {
        db.tagDao().insertTag(tagEntityList)
    }

    suspend fun insertAllComments(comments: List<CommentEntity>?) {
        comments?.let {
            for (comment in comments) {
                insertComment(comment)
            }
        }
    }

    suspend fun insertTagArticle(tagArticleEntity: TagAndArticleEntity) {
        db.withTransaction {
            db.tagArticleDao().insertTagAndArticle(
                tagArticleEntity
            )
        }
    }

    suspend fun insertTagArticle(tagArticleEntity: List<TagAndArticleEntity>) {
        db.withTransaction {
            db.tagArticleDao().insertTagAndArticle(
                tagArticleEntity
            )
        }
    }

    suspend fun test(articleNetworks: List<ArticleNetwork>?) {
        db.withTransaction {
            articleNetworks?.forEach { article ->
                db.tagArticleDao().insertTagAndArticle(
                    article.tagList.map { tag ->
                        TagAndArticleEntity(tag, article.slug)
                    }
                )
            }
        }
    }

    suspend fun insertTagArticles(tag: String, articleNetworks: List<ArticleNetwork>?) {
        articleNetworks?.let {
            it.forEach { article ->
                val isFeed = db.articleDao().getArticle(article.slug).isFeed
                db.articleDao().insertArticle(article.toArticleEntity(isFeed))
                db.tagArticleDao().insertTagAndArticle(
                    TagAndArticleEntity(
                        tag = tag,
                        slug = article.slug
                    )
                )
            }
        }
    }

    suspend fun insertTags(allArticles: List<ArticleNetwork>?) {
        allArticles?.let { articleNetworks ->
            db.withTransaction {
                articleNetworks.forEach {
                    db.tagDao().insertTag(it.tagList.map { tag ->
                        TagEntity(tag)
                    })
                }
            }
        }
    }

    private suspend fun insertComment(commentEntity: CommentEntity) {
        db.commentDao().insertComment(commentEntity)
    }

    fun getLikedArticlesSlugs() = db.likesDao().getLikedArticlesSlugs()

    fun getUser(username: String) = db.userDao().getUser(username)

    fun getArticle(slug: String) = db.articleDao().getArticle(slug)

    fun getArticleTags(slug: String) = db.tagDao().getArticleTags(slug)

    fun getArticleComments(slug: String) = db.commentDao().getArticleComments(slug)

    fun getTagArticles(tag: String) = db.tagArticleDao().getTagArticles(tag)

    fun updateArticle(slug: String) {
        val article = getArticle(slug)
        article.liked?.let { flag ->
            article.liked = !flag
        }
        db.articleDao().updateArticle(article)
    }

    /*      ARTICLE DRAFT      */
    fun saveDraft(title: String, body: String) {
        settings.titleDraft = title
        settings.bodyDraft = body
    }

    fun getTitleDraft() = settings.titleDraft

    fun getBodyDraft() = settings.bodyDraft

}