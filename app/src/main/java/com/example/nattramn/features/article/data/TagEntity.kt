package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true) val tagId: Int,
    val tag: String,
    val articleSlug: String
) {
    companion object {
        fun convertTag(slug: String, tag: String): TagEntity {
            return TagEntity(
                tagId = 0,
                tag = tag,
                articleSlug = slug
            )
        }
    }
}