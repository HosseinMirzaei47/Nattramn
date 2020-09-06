package com.example.nattramn.features.article.data

import androidx.lifecycle.LiveData
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
    fun getAllArticles(): LiveData<List<ArticleEntity>>

    @Transaction
    @Query("select * from articles")
    fun getArticleTags(): LiveData<List<ArticleAndTags>>

    @Transaction
    @Query("select * from articles")
    fun getArticleComments(): LiveData<List<ArticleAndComments>>

    @Query("select * from articles where title like :title")
    fun getArticle(title: String): LiveData<ArticleEntity>

}