package com.example.nattramn.core

import androidx.room.*
import com.example.nattramn.features.article.data.ArticleAndComments
import com.example.nattramn.features.article.data.ArticleAndTags
import com.example.nattramn.features.article.data.ArticleEntity
import java.util.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleEntity: ArticleEntity)

    @Delete
    fun deleteArticle(articleEntity: ArticleEntity)

    @Transaction
    @Query("select * from articles")
    fun getArticleComments(articleEntity: ArticleEntity): ArrayList<ArticleAndComments>

    @Transaction
    @Query("select * from articles")
    fun getArticleTags(articleEntity: ArticleEntity): ArrayList<ArticleAndTags>

    /*@Transaction
    @Query("select * from articles")
    fun getArticleTagsAndComments(articleEntity: ArticleEntity): ArrayList<ArticlesAndTagsAndComments>*/

}