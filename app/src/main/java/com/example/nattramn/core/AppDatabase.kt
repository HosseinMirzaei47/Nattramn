package com.example.nattramn.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nattramn.features.article.data.ArticleEntity
import com.example.nattramn.features.article.data.CommentEntity
import com.example.nattramn.features.article.data.TagEntity
import com.example.nattramn.features.user.data.UserEntity

@Database(
    entities = [ArticleEntity::class, UserEntity::class, TagEntity::class, CommentEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun userDao(): UserDao

    companion object {

        private const val databaseName = "nattramn-db"

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

    }

}