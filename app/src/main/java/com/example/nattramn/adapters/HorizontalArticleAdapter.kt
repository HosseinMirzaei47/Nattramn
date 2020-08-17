package com.example.nattramn.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nattramn.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.horizontal_article_row.view.*

class HorizontalArticleAdapter(private val context: Context) :
    RecyclerView.Adapter<HorizontalArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_article_row, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 3 == 0) {
            holder.bookmarkItem.setImageResource(R.drawable.ic_bookmark)
            holder.authorImage.setImageResource(R.drawable.test01)
            holder.articlePreview.text = context.resources.getString(R.string.news)
        }
    }

    override fun getItemCount(): Int {
        return 20
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorImage: CircleImageView = itemView.itemAuthorImage
        val bookmarkItem: ImageButton = itemView.itemBookmark
        val articlePreview: TextView = itemView.itemArticlePreview
        /*val authorName: TextView = itemView.itemAuthorName
        val articleTime: TextView = itemView.itemTime*/
    }

}