package com.example.nattramn.features.article.data

import androidx.room.*

@Dao
interface LikesDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun likeArticle(likesEntity: LikesEntity)

    @Query("delete from likes where slug =:slug")
    fun unlikeArticle(slug: String)

    @Query("select slug from likes")
    fun getLikedArticlesSlugs(): List<String>

}