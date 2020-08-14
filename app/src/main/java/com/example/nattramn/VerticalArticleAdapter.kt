package com.example.nattramn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.vertical_article_row.view.*

class VerticalArticleAdapter(private val context: Context) :
    RecyclerView.Adapter<VerticalArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vertical_article_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 3 == 0) {
            holder.bookmarkItem.setImageResource(R.drawable.ic_bookmark)
            holder.authorImage.setImageResource(R.drawable.test01)
            holder.articlePreview.text = context.resources.getString(R.string.news)
        }
    }

    override fun getItemCount(): Int {
        return 15
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorImage: CircleImageView = itemView.itemAuthorImage
        val authorName: TextView = itemView.itemAuthorName
        val articleTime: TextView = itemView.itemTime
        val bookmarkItem: ImageButton = itemView.itemBookmark
        val articlePreview: TextView = itemView.itemArticlePreview
    }

}