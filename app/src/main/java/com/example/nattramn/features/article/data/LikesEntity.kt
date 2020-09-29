package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likes")
data class LikesEntity(
    @PrimaryKey(autoGenerate = true) val likeId: Int,
    val slug: String
)