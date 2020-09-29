package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "likes"/*,
    primaryKeys = ["likeId"],
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            onUpdate = CASCADE,
            entity = ArticleEntity::class,
            parentColumns = ["slug"],
            childColumns = ["slug"]
        )
    ]*/
)
data class LikesEntity(
    @PrimaryKey(autoGenerate = true) val likeId: Int,
    val slug: String
)