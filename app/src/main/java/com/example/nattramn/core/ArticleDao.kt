package com.example.nattramn.core

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.nattramn.features.article.data.ArticleEntity

@Dao
interface ArticleDao {

    /*@Query("select * from articles")
    fun getAllArticles(): ArrayList<ArticleEntity>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleEntity: ArticleEntity)

    @Delete
    fun deleteArticle(articleEntity: ArticleEntity)

    /*@Transaction
    @Query("select * from articles")
    fun getArticleComments(articleEntity: ArticleEntity): List<ArticleAndComments>

    @Transaction
    @Query("select * from articles")
    fun getArticleTags(articleEntity: ArticleEntity): List<ArticleAndTags>*/

    /*@Transaction
    @Query("select * from articles")
    fun getArticleTagsAndComments(articleEntity: ArticleEntity): ArrayList<ArticlesAndTagsAndComments>*/

}