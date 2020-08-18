package com.example.nattramn.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nattramn.R
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.profile_article_row.view.*

class ProfileArticleAdapter(private val context: Context) :
    RecyclerView.Adapter<ProfileArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.profile_article_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 3 == 0) {

            Glide
                .with(context)
                .load(R.drawable.test01)
                .into(holder.authorImage)

            holder.articleBookmark.setImageResource(R.drawable.ic_bookmark_checked)

            holder.articleDescription.text = context.resources.getString(R.string.news)
            holder.articleLikes.text = context.getString(R.string.oneK)

        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val authorImage: CircleImageView = itemView.itemArticleAuthorImage
        val articleBookmark: ImageButton = itemView.itemArticleBookmark
        val articleDescription: TextView = itemView.itemArticleDescription
        val articleLikes: MaterialButton = itemView.itemLikesIcon
        /*val articleTitle: TextView = itemView.itemArticleTitle
        val articleComments: MaterialButton = itemView.itemCommentsButton
        val authorName: TextView = itemView.itemArticleAuthorName
        val articleDate: TextView = itemView.itemArticleDate
        val articleOptions: ImageButton = itemView.itemArticleOptions*/

    }

}
