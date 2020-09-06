package com.example.nattramn.core

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    /*private val gson = Gson()

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
    }*/

}