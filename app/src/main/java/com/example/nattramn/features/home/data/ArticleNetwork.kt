package com.example.nattramn.features.home.data


import com.example.nattramn.R
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.data.models.ArticleComments
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import com.example.nattramn.features.article.ui.TagView
import com.example.nattramn.features.user.ui.UserView
import com.google.gson.annotations.SerializedName

data class ArticleNetwork(
    @SerializedName("author")
    val author: Author,
    @SerializedName("body")
    val body: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("favorited")
    val isBookmarked: Boolean,
    @SerializedName("favoritesCount")
    val favoritesCount: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("tagList")
    val tagList: ArrayList<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String
) {
    fun toArticleView(articleComments: Resource<ArticleComments>): ArticleView {

        val tagView = TagView(tagList)

        val comments = articleComments.data?.comments?.map {
            CommentView(it.author.username, it.author.image, it.body)
        } ?: listOf()

        val user = UserView(
            author.username,
            MyApp.app.resources.getString(R.string.job),
            author.image,
            85,
            !author.following
        )

        return ArticleView(
            user,
            createdAt,
            title,
            body,
            arrayListOf(tagView),
            comments,
            favoritesCount.toString(),
            comments.size,
            isBookmarked,
            slug
        )
    }
}