package com.example.nattramn.features.home.data.models


import com.example.nattramn.features.article.data.TagEntity
import com.google.gson.annotations.SerializedName

data class AllTagsResponse(
    @SerializedName("tags")
    val tags: List<String>
) {
    fun toTagEntity(tag: String): TagEntity {
        return TagEntity(
            tag
        )
    }
}