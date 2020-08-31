package com.example.nattramn.core

import android.content.Context
import com.example.nattramn.R
import com.example.nattramn.models.Article
import com.example.nattramn.models.Comment
import com.example.nattramn.models.User

class Utils(val context: Context) {

    private val articles = arrayListOf<Article>()
    private val comments = arrayListOf<Comment>()
    val user = User(
        "حسین میرزایی",
        "مدرس زبان انگلیسی",
        "https://upload.wikimedia.org/wikipedia/commons/a/a0/Pierre-Person.jpg",
        145,
        ArrayList(),
        ArrayList(),
        true
    )

    fun initArticles(): ArrayList<Article> {

        repeat(10) {

            articles.add(

                Article(
                    user,
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

    fun initComments(): ArrayList<Comment> {

        comments.add(
            Comment(
                user,
                R.drawable.test01,
                context.getString(R.string.sampleComment)
            )
        )
        comments.add(
            Comment(
                user,
                R.drawable.test02,
                context.getString(R.string.sampleComment)
            )
        )
        comments.add(
            Comment(
                user,
                R.drawable.test01,
                context.getString(R.string.sampleComment)
            )
        )

        return comments

    }

}