package com.example.nattramn.features.article.data

import androidx.room.PrimaryKey
import java.util.*

data class TagEntity(
    @PrimaryKey val tagId: Int,
    val arrayList: ArrayList<String>,
    val tagOwnerArticleId: Long
)