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
        ArrayList(),
        ArrayList(),
        true
    )

    val MIGRATION_1_2 = object : Migration(1, 2) {
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
                    ArrayList(),
                    "2.5k",
                    50,
                    true
                )
            )

        }

        return articles

    }

    fun initComments(): ArrayList<CommentView> {

        comments.add(
            CommentView(
                userView,
                R.drawable.test01,
                context.getString(R.string.sampleComment)
            )
        )
        comments.add(
            CommentView(
                userView,
                R.drawable.test02,
                context.getString(R.string.sampleComment)
            )
        )
        comments.add(
            CommentView(
                userView,
                R.drawable.test01,
                context.getString(R.string.sampleComment)
            )
        )

        return comments

    }

}