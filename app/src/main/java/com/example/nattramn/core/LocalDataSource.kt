package com.example.nattramn.core

import com.example.nattramn.core.storage.data.PreferenceProperty.Companion.getPreferences
import com.example.nattramn.core.storage.data.Settings
import com.example.nattramn.features.article.data.ArticleEntity
import com.example.nattramn.features.article.data.CommentEntity
import com.example.nattramn.features.article.data.TagEntity
import com.example.nattramn.features.user.data.UserEntity

class LocalDataSource() {

    private val settings = Settings(MyApp.app.getPreferences())
    private val db = AppDatabase.buildDatabase(MyApp.app)

    fun insertArticle(articleEntity: ArticleEntity) {
        db.articleDao().insertArticle(articleEntity)
    }

    fun insertUser(userEntity: UserEntity) {
        db.userDao().insertUser(userEntity)
    }

    fun insertComment(commentEntity: CommentEntity) {
        db.commentDao().insertComment(commentEntity)
    }

    fun insertTag(tagEntity: TagEntity) {
        db.tagDao().insertTag(tagEntity)
    }

    fun insertAllArticles(articles: List<ArticleEntity>) {
        for (article in articles) {
            insertArticle(article)
        }
    }

    fun insertAllTags(tags: List<TagEntity>) {
        for (tag in tags) {
            insertTag(tag)
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

}