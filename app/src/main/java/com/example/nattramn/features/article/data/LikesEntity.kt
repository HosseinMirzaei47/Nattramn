package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "likes",
    primaryKeys = ["slug"],
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            entity = ArticleEntity::class,
            parentColumns = ["slug"],
            childColumns = ["slug"]
        )
    ]
)
data class LikesEntity(
    val slug: String
)