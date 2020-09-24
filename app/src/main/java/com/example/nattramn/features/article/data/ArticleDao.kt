package com.example.nattramn.features.article.data

import androidx.lifecycle.LiveData
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

    @Query("select * from articles where slug like :slug")
    fun getArticle(slug: String): LiveData<List<ArticleEntity>>

    @Update
    fun editArticle(articleEntity: ArticleEntity)

    @Query("select * from articles")
    fun getAllArticles(): List<ArticleEntity>

    @Query("select * from articles where isFeed = 1")
    fun getFeedArticles(): List<ArticleEntity>

}