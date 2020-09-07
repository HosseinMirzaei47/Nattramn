package com.example.nattramn.features.article.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Query("delete from articles")
    fun clearArticleTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleEntity: ArticleEntity)

    @Delete
    fun deleteArticle(articleEntity: ArticleEntity)

    @Update
    fun editArticle(articleEntity: ArticleEntity)

    @Query("select * from articles order by articleId desc")
    fun getAllArticles(): LiveData<List<ArticleEntity>>

    @Query("select * from articles order by articleId desc")
    fun getAllArticles2(): List<ArticleEntity>

    @Query("select * from articles where title like :title")
    fun getArticle(title: String): LiveData<ArticleEntity>

}