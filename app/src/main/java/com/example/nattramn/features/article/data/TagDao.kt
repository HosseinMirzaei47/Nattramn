package com.example.nattramn.features.article.data

import androidx.room.*

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(tagEntity: TagEntity)

    @Delete
    fun deleteTag(tagEntity: TagEntity)

    @Update
    fun editTag(tagEntity: TagEntity)

}