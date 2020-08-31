package com.example.nattramn.features.user.data

import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nattramn.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class User(
    val name: String,
    val job: String,
    val image: String,
    val followers: Int,
    val favoriteArticles: @RawValue ArrayList<Article>,
    val publishedArticle: @RawValue ArrayList<Article>,
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