package com.example.nattramn.features.article.data

import androidx.room.*

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addComment(commentEntity: CommentEntity)

    @Delete
    fun deleteComment(commentEntity: CommentEntity)

    @Update
    fun editComment(commentEntity: CommentEntity)

}