package com.example.nattramn.features.article.data

import com.example.nattramn.core.AppDatabase
import com.example.nattramn.core.MyApp
import com.example.nattramn.features.user.data.UserEntity

class ArticleLocalDataSource {

    private val db = AppDatabase.buildDatabase(MyApp.app)

    suspend fun insertArticle(articleEntity: ArticleEntity) {
        db.articleDao().insertArticle(articleEntity)
    }

    suspend fun insertAllArticlesAndTags(articles: List<ArticleEntity>?) {
        if (articles != null) {
            for (article in articles) {
                insertArticle(article)
                db.tagDao().insertTag(article.tags)
            }
        }
    }

    suspend fun insertUser(userEntity: UserEntity) {
        db.userDao().insertUser(userEntity)
    }

    suspend fun insertAllTags(tagEntityList: List<TagEntity>?) {
        db.tagDao().insertTag(tagEntityList)
    }

    fun insertAllComments(comments: List<CommentEntity>) {
        for (comment in comments) {
            insertComment(comment)
        }
    }

    fun insertComment(commentEntity: CommentEntity) {
        db.commentDao().insertComment(commentEntity)
    }

    fun getArticle(slug: String) {
        db.articleDao().getArticle(slug)
    }

}