package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey val tagId: Int,
    val arrayList: ArrayList<String>,
    val tagOwnerArticleId: Long
)