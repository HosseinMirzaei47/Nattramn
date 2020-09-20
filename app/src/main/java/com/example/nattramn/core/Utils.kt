package com.example.nattramn.core

import android.view.View
import com.example.nattramn.features.article.data.ArticleEntity
import com.example.nattramn.features.article.data.CommentEntity
import com.example.nattramn.features.article.data.TagEntity
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.UserEntity
import com.google.android.material.snackbar.Snackbar

fun snackMaker(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}

fun toArticleView(
    userEntity: UserEntity?,
    articleEntity: ArticleEntity?,
    tagsEntity: List<TagEntity>?,
    commentsEntity: List<CommentEntity>?
): ArticleView {
    return ArticleView(
        userView = userEntity!!.toUserView(),
        date = articleEntity!!.date,
        title = articleEntity.title,
        body = articleEntity.body,
        likes = articleEntity.likes,
        tags = tagsEntity!!.map { it.toTagString() },
        commentViews = commentsEntity?.map { it.toCommentView() }!!,
        commentsNumber = commentsEntity.size,
        bookmarked = articleEntity.bookmarked,
        slug = articleEntity.slug
    )
}
