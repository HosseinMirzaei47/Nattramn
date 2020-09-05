package com.example.nattramn.core

import androidx.room.TypeConverter
import com.example.nattramn.features.article.data.CommentEntity
import com.example.nattramn.features.article.data.SuggestionsEntity
import com.example.nattramn.features.article.data.TagEntity
import com.example.nattramn.features.user.data.UserEntity
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun userToString(userEntity: UserEntity): String {
        if (userEntity.name.isEmpty()) return ""
        return gson.toJson(userEntity)
    }

    @TypeConverter
    fun stringToUser(user: String): UserEntity {
        return gson.fromJson(user, UserEntity::class.java)
    }

    @TypeConverter
    fun tagToString(tagEntity: TagEntity): String {
        return gson.toJson(tagEntity)
    }

    @TypeConverter
    fun stringToTag(tag: String): TagEntity {
        return gson.fromJson(tag, TagEntity::class.java)
    }

    @TypeConverter
    fun commentToString(commentEntity: CommentEntity): String {
        return gson.toJson(commentEntity)
    }

    @TypeConverter
    fun stringToComment(comment: String): CommentEntity {
        return gson.fromJson(comment, CommentEntity::class.java)
    }

    @TypeConverter
    fun sugToString(suggestionsEntity: SuggestionsEntity): String {
        return gson.toJson(suggestionsEntity)
    }

    @TypeConverter
    fun stringToSug(sug: String): SuggestionsEntity {
        return gson.fromJson(sug, SuggestionsEntity::class.java)
    }

}