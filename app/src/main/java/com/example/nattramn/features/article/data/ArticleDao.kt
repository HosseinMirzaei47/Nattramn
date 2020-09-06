package com.example.nattramn.features.article.data

import androidx.room.*

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

    @Query("select * from articles where title like :title")
    fun getArticle(title: String): ArticleEntity

}