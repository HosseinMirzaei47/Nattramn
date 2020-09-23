package com.example.nattramn.features.article.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.nattramn.features.article.data.models.TagAndArticleEntity

@Dao
interface TagAndArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTagAndArticle(tagAndArticleEntity: TagAndArticleEntity)

}