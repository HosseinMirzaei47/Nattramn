package com.example.nattramn.features.article.data

import androidx.room.PrimaryKey

data class SuggestionsEntity(
    @PrimaryKey val suggestionId: Int,
    val suggestions: ArrayList<ArticleEntity>,
    val suggestedOwnerArticleId: Long
)