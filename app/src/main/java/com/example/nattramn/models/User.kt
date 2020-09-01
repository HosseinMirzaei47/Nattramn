package com.example.nattramn.models

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
data class User(
    val name: String,
    val job: String,
    val image: String,
    val followers: Int,
    val favoriteArticleViews: @RawValue ArrayList<ArticleView>,
    val publishedArticleView: @RawValue ArrayList<ArticleView>,
    val isMe: Boolean
) : Parcelable

@BindingAdapter("imageSource")
fun setImageUrl(imageView: ImageView, imageSource: String?) {

    imageSource?.let {
        /*val imgUri = it.toUri().buildUpon().scheme("https://ibb.co/19Z3rbG").build()*/
        Glide.with(imageView.context)
            .load(imageSource)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.default_profile_picture)
            )
            .into(imageView)
    }
}

@BindingAdapter("textNumber")
fun setTextNumber(textView: TextView, number: Int) {
    textView.text = number.toString()
}