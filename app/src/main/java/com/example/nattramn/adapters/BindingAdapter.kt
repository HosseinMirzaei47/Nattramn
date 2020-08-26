package com.example.nattramn.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nattramn.R

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, imageSource: String?) {

    imageSource?.let {
        /*val imgUri = it.toUri().buildUpon().scheme("https://ibb.co/19Z3rbG").build()*/
        Glide.with(imageView.context)
            .load(imageSource)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.test01)
            )
            .into(imageView)
    }
}