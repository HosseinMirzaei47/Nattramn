package com.example.nattramn.core

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nattramn.R
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.google.android.material.button.MaterialButton

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

@BindingAdapter("app:isBookmarked")
fun bookmarkSrc(view: View, isBookmarked: Boolean) {

    if (view is ImageView) {
        if (isBookmarked) {
            load(view, R.drawable.ic_bookmark_article_fragment)
        } else {
            load(view, R.drawable.ic_bookmark)
        }
    }

}

private fun load(view: ImageView, image: Int) {
    Glide.with(view.context)
        .load(image)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.ic_bookmark_article_fragment)
        )
        .into(view)
}

@BindingAdapter("textNumber")
fun setTextNumber(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("app:visibleOnResult")
fun visibleOnResult(view: View, resource: Resource<*>?) {
    view.isVisible = resource?.status == Status.LOADING
}

@BindingAdapter("app:checkState")
fun checkState(view: View, following: Boolean) {
    if (view is MaterialButton && following) {
        view.text = "در حال دنبال کردن"
        view.setTextColor(Color.parseColor("#ffffff"))
        view.setBackgroundColor(Color.parseColor("#63b47c"))
    }
}