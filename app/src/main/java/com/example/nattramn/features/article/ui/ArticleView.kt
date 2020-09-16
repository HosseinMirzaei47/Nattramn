package com.example.nattramn.features.article.ui

import android.os.Parcelable
import com.example.nattramn.features.user.ui.UserView
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ArticleView(
    val userView: UserView,
    val date: String,
    val title: String,
    val body: String,
    val tagViews: @RawValue ArrayList<TagView>,
    val commentViews: @RawValue List<CommentView>,
    val likes: String,
    val commentsNumber: Int,
    val bookmarked: Boolean,
    val slug: String
) : Parcelable