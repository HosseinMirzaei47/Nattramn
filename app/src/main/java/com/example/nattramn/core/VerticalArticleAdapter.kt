package com.example.nattramn.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nattramn.R
import com.example.nattramn.databinding.VerticalArticleRowBinding
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.OnArticleListener
import java.util.*

class VerticalArticleAdapter(
    var articleViews: ArrayList<ArticleView>,
    private val onArticleListener: OnArticleListener
) :
    RecyclerView.Adapter<VerticalArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: VerticalArticleRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.vertical_article_row, parent, false
        )

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.article = articleViews[position]

    }

    override fun getItemCount() = articleViews.size

    inner class ViewHolder(val binding: VerticalArticleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.verticalArticleCard.setOnClickListener {
                onArticleListener.onCardClick(
                    layoutPosition
                )
            }
            binding.itemBookmark.setOnClickListener {
                onArticleListener.onArticleSaveClick(
                    layoutPosition
                )
            }
            binding.itemArticlePreview.setOnClickListener {
                onArticleListener.onArticleTitleClick(
                    layoutPosition
                )
            }
            binding.itemAuthorImage.setOnClickListener {
                onArticleListener.onAuthorIconClick(
                    layoutPosition
                )
            }
            binding.itemAuthorName.setOnClickListener {
                onArticleListener.onAuthorNameClick(
                    layoutPosition
                )
            }

        }

    }

}