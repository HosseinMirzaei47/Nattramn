package com.example.nattramn.features.article.data

import androidx.room.*

@Dao
interface LikesDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLikedArticle(likesEntity: LikesEntity)

    @Delete
    fun unlikeArticle(likesEntity: LikesEntity)

    @Query("select * from likes")
    fun getLikedArticlesSlugs(): List<LikesEntity>

}