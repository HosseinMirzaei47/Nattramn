package com.example.nattramn.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nattramn.R
import com.example.nattramn.databinding.CommentRowBinding
import com.example.nattramn.models.Comment
import com.example.nattramn.recyclerItemListeners.OnCommentListener

class CommentAdapter(
    private val comments: ArrayList<Comment>,
    private val commentListener: OnCommentListener
) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: CommentRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.comment_row, parent, false
        )

        return ViewHolder(binding)

    }

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.comment = comments[position]

    }

    inner class ViewHolder(binding: CommentRowBinding) : RecyclerView.ViewHolder(binding.root) {


        val binding: CommentRowBinding = binding

        init {

            binding.itemCommentName.setOnClickListener {
                commentListener.onCommentIconClick(
                    layoutPosition
                )
            }
            binding.itemCommentImage.setOnClickListener {
                commentListener.onCommentIconClick(
                    layoutPosition
                )
            }

        }

    }

}
