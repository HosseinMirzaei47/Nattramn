package com.example.nattramn.features.article.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TagDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tagEntity: List<TagEntity>?)

    @Delete
    fun deleteTag(tagEntity: TagEntity)

    @Update
    fun editTag(tagEntity: TagEntity)

    @Query("select * from tags where tag in (select tag from tagsArticles where slug like :slug)")
    fun getArticleTags(slug: String): List<TagEntity>

    @Query("select * from tags")
    fun getAllTags(): LiveData<List<TagEntity>>

}