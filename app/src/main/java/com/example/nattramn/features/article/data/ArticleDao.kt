package com.example.nattramn.features.article.data

import androidx.room.*

@Dao
interface ArticleDao {

    @Query("delete from articles")
    fun clearArticleTable()

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleEntity: List<ArticleEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleEntity: ArticleEntity)

    @Delete
    fun deleteArticle(articleEntity: ArticleEntity)

    @Query("delete from articles where slug =:slug")
    fun deleteArticle(slug: String)

    @Update
    fun updateArticle(articleEntity: ArticleEntity)

    @Query("select * from articles")
    fun getAllArticles(): List<ArticleEntity>

    @Query("select * from articles where isFeed = 1 order by date")
    fun getFeedArticles(): List<ArticleEntity>

    @Query("select * from articles where slug =:slug")
    fun getArticle(slug: String): ArticleEntity

    @Query("select * from articles where ownerUsername =:username")
    fun getUserArticles(username: String): List<ArticleEntity>

    @Query("select * from articles where bookmarked = 1")
    fun getBookmarkedArticles(): List<ArticleEntity>

    @Query("select * from articles where title like :title")
    fun searchByTitle(title: String): List<ArticleEntity>

}