package com.example.nattramn.core

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nattramn.R
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import com.example.nattramn.features.user.ui.UserView

class Utils(val context: Context) {

    private val articles = arrayListOf<ArticleView>()
    private val comments = arrayListOf<CommentView>()
    val userView = UserView(
        "حسین میرزایی",
        "مدرس زبان انگلیسی",
        "https://upload.wikimedia.org/wikipedia/commons/a/a0/Pierre-Person.jpg",
        145,
        true
    )

    val MIGRATION_1_2 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "alter table users add column testColumn text"
            )
        }
    }

    fun initArticles(): ArrayList<ArticleView> {

        repeat(10) {

            articles.add(

                ArticleView(
                    userView,
                    context.getString(R.string.sampleDate),
                    context.getString(R.string.news),
                    context.getString(R.string.news),
                    ArrayList(),
                    ArrayList(),
                    "2.5k",
                    50,
                    true,
                    "invalid"
                )
            )

        }

        return articles

    }

    fun initComments(): ArrayList<CommentView> {

        comments.add(
            CommentView(
                userView.name,
                userView.image,
                context.getString(R.string.sampleComment)
            )
        )
        comments.add(
            CommentView(
                userView.name,
                userView.image,
                context.getString(R.string.sampleComment)
            )
        )
        comments.add(
            CommentView(
                userView.name,
                userView.image,
                context.getString(R.string.sampleComment)
            )
        )

        return comments

    }

    suspend inline fun <T> safeApiCall(responseFunction: () -> T): T? {
        return try {
            responseFunction.invoke()//Or responseFunction()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}