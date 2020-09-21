package com.example.nattramn.features.article.data.models

import androidx.room.Entity

@Entity(
    tableName = "tagArticles",
    primaryKeys = ["tag", "slug"]
)
data class TagAndArticleEntity(
    val tag: String,
    val slug: String
)