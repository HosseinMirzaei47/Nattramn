package com.example.nattramn.models

import android.widget.TextView
import androidx.databinding.BindingAdapter

data class Article(
    val user: User,
    val date: String,
    val title: String,
    val body: String,
    val tags: ArrayList<Tag>,
    val comments: ArrayList<Comment>,
    val suggestions: ArrayList<Suggestions>,
    val likes: String,
    val commentsNumber: Int,
    val bookmarked: Boolean
)

private val faNumbers =
    arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")

@BindingAdapter("persianText")
fun convert(textView: TextView, text: String) {

    var out: String? = ""
    val length = text.length

    for (i in 0 until length) {
        val c = text[i]
        if (c in '0'..'9') {
            val number = c.toString().toInt()
            out += faNumbers[number]
        } else if (c == '٫' || c == ',') {
            out += '،'
        } else {
            out += c
        }
    }

    textView.text = out

}