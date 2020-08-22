package com.example.nattramn.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nattramn.R
import com.example.nattramn.recyclerItemListeners.OnArticleListener
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.horizontal_article_row.view.*

class SuggestedArticleAdapter(
    private val context: Context,
    private val onArticleListener: OnArticleListener
) :
    RecyclerView.Adapter<SuggestedArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_article_row, parent, false)
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
        return 10
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val authorImage: CircleImageView = itemView.itemAuthorImage
        val bookmarkItem: ImageButton = itemView.itemBookmark
        val articlePreview: TextView = itemView.itemArticlePreview
        val authorName: TextView = itemView.itemAuthorName
        /*val articleTime: TextView = itemView.itemTime*/

        init {

            bookmarkItem.setOnClickListener { onArticleListener.onArticleSaveClick(layoutPosition) }
            articlePreview.setOnClickListener { onArticleListener.onArticleTitleClick(layoutPosition) }
            authorImage.setOnClickListener { onArticleListener.onAuthorIconClick(layoutPosition) }
            authorName.setOnClickListener { onArticleListener.onAuthorNameClick(layoutPosition) }

        }

    }

}