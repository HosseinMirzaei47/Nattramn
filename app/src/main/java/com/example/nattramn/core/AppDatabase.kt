package com.example.nattramn.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nattramn.features.article.data.*
import com.example.nattramn.features.article.data.models.TagAndArticleEntity
import com.example.nattramn.features.user.data.UserDao
import com.example.nattramn.features.user.data.UserEntity

@Database(
    entities = [ArticleEntity::class, UserEntity::class, TagEntity::class, CommentEntity::class, TagAndArticleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao
    abstract fun tagArticleDao(): TagAndArticleDao
    abstract fun tagDao(): TagDao

    companion object {

        private const val databaseName = "nattramn-db"

        fun buildDatabase(
            context: Context
            /*migration: Migration*/
        ): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                /*.addMigrations(migration)*/
                .build()
        }

    }

}