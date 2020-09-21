package com.example.nattramn.features.article.data

import androidx.room.*

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(commentEntity: CommentEntity)

    @Delete
    fun deleteComment(commentEntity: CommentEntity)

    @Update
    fun editComment(commentEntity: CommentEntity)

    @Query("select * from comments where articleSlug like :slug")
    fun getArticleComments(slug: String): List<CommentEntity>

}