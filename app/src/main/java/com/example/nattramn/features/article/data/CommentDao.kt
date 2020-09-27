package com.example.nattramn.features.article.data

import androidx.room.*

@Dao
interface CommentDao {

    @Query("delete from comments")
    fun clearCommentsTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(commentEntity: CommentEntity)

    @Delete
    fun deleteComment(commentEntity: CommentEntity)

    @Update
    fun editComment(commentEntity: CommentEntity)

    @Query("select * from comments where articleSlug like :slug")
    fun getArticleComments(slug: String): List<CommentEntity>

}