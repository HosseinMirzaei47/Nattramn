package com.example.nattramn.core

import androidx.room.*
import com.example.nattramn.features.article.data.ArticleAndComments
import com.example.nattramn.features.article.data.ArticleAndTags
import com.example.nattramn.features.article.data.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleEntity: ArticleEntity)

    @Delete
    fun deleteArticle(articleEntity: ArticleEntity)

    @Update
    fun editArticle(articleEntity: ArticleEntity)

    @Query("select * from articles order by articleId desc")
    fun getAllArticles(): List<ArticleEntity>

    @Transaction
    @Query("select * from articles")
    fun getArticleTags(): List<ArticleAndTags>

    @Transaction
    @Query("select * from articles")
    fun getArticleComments(): List<ArticleAndComments>

}