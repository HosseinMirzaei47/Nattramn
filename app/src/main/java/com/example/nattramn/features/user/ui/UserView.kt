package com.example.nattramn.features.user.ui

import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nattramn.R
import com.example.nattramn.features.article.ui.ArticleView
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class UserView(
    val name: String,
    val job: String,
    val image: String,
    val followers: Int,
    val favoriteArticleViews: @RawValue ArrayList<ArticleView>,
    val publishedArticleView: @RawValue ArrayList<ArticleView>,
    val isMe: Boolean
) : Parcelable