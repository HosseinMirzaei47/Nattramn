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

    @Query("select * from articles")
    fun getAllArticles(): LiveData<List<ArticleEntity>>

    @Query("select * from articles where slug like :slug")
    fun getArticle(slug: String): LiveData<List<ArticleEntity>>

}