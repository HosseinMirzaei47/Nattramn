package com.example.nattramn.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nattramn.R
import com.example.nattramn.databinding.HorizontalArticleRowBinding
import com.example.nattramn.models.Article
import com.example.nattramn.recyclerItemListeners.OnArticleListener
import java.util.*

class SuggestedArticleAdapter(
    var suggestions: ArrayList<Article>,
    private val onArticleListener: OnArticleListener
) :
    RecyclerView.Adapter<SuggestedArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: HorizontalArticleRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.horizontal_article_row, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.article = suggestions[position]

    }

    override fun getItemCount() = suggestions.size

    inner class ViewHolder(val binding: HorizontalArticleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.horizontalArticleCard.setOnClickListener {
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