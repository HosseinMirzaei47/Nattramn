package com.example.nattramn.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nattramn.R
import com.example.nattramn.recyclerItemListeners.OnCommentListener
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.comment_row.view.*

class CommentAdapter(
    private val commentListener: OnCommentListener
) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 3 == 0) {
            holder.image.setImageResource(R.drawable.test01)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: CircleImageView = itemView.itemCommentImage
        val name: TextView = itemView.itemCommentName
        /*val comment: TextView = itemView.itemCommentText*/

        init {

            name.setOnClickListener { commentListener.onCommentIconClick(layoutPosition) }
            image.setOnClickListener { commentListener.onCommentIconClick(layoutPosition) }

        }

    }

}
