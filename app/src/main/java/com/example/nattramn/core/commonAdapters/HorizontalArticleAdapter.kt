package com.example.nattramn.core.commonAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nattramn.core.utils.Constants
import com.example.nattramn.databinding.HorizontalArticleRowBinding
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.OnArticleListener

class HorizontalArticleAdapter(
    var articleViews: List<ArticleView>,
    private val onArticleListener: OnArticleListener
) :
    RecyclerView.Adapter<HorizontalArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = HorizontalArticleRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.article = articleViews[position]
    }

    override fun getItemCount() = articleViews.size

    inner class ViewHolder(val binding: HorizontalArticleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.horizontalArticleCard.setOnClickListener {
                onArticleListener.onCardClick(
                    articleViews[layoutPosition].slug
                )
            }
            binding.itemBookmark.setOnClickListener {
                onArticleListener.onArticleSaveClick(
                    articleViews[layoutPosition].slug,
                    articleViews[layoutPosition].bookmarked,
                    layoutPosition,
                    Constants.LATEST
                )
            }
            binding.itemArticlePreview.setOnClickListener {
                onArticleListener.onArticleTitleClick(
                    articleViews[layoutPosition].slug
                )
            }
            binding.itemAuthorImage.setOnClickListener {
                onArticleListener.onAuthorIconClick(
                    articleViews[layoutPosition].userView.name
                )
            }
            binding.itemAuthorName.setOnClickListener {
                onArticleListener.onAuthorNameClick(
                    articleViews[layoutPosition].userView.name
                )
            }

        }

    }

}