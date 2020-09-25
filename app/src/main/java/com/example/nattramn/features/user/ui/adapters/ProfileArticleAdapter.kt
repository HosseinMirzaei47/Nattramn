package com.example.nattramn.features.user.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nattramn.R
import com.example.nattramn.databinding.ProfileArticleRowBinding
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.ui.OnProfileArticleListener

class ProfileArticleAdapter(
    var profileArticleViews: List<ArticleView>,
    private val onProfileArticleListener: OnProfileArticleListener
) :
    RecyclerView.Adapter<ProfileArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ProfileArticleRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.profile_article_row, parent, false
        )

        return ViewHolder(binding)

    }

    override fun getItemCount() = profileArticleViews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.profileData = profileArticleViews[position]
    }

    inner class ViewHolder(val binding: ProfileArticleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.profileArticleCard.setOnClickListener {
                onProfileArticleListener.onProfileArticleCardClick(
                    profileArticleViews[layoutPosition].slug
                )
            }
            binding.itemArticleAuthorImage.setOnClickListener {
                onProfileArticleListener.onAuthorIconClick(
                    profileArticleViews[layoutPosition].userView.name
                )
            }
            binding.itemArticleAuthorName.setOnClickListener {
                onProfileArticleListener.onAuthorNameClick(
                    profileArticleViews[layoutPosition].userView.name
                )
            }
            binding.itemArticleBookmark.setOnClickListener {
                onProfileArticleListener.onBookmarkClick(
                    profileArticleViews[layoutPosition].slug
                )
            }
            binding.itemArticleDescription.setOnClickListener {
                onProfileArticleListener.onArticleDescriptionClick(
                    profileArticleViews[layoutPosition].slug
                )
            }
            binding.itemArticleTitle.setOnClickListener {
                onProfileArticleListener.onArticleTitleClick(
                    profileArticleViews[layoutPosition].slug
                )
            }
            binding.itemCommentsButton.setOnClickListener {
                onProfileArticleListener.onArticleCommentsClick(
                    layoutPosition
                )
            }
            binding.itemArticleOptions.setOnClickListener {
                onProfileArticleListener.onMoreOptionsClick(
                    profileArticleViews[layoutPosition].slug,
                    layoutPosition
                )
            }

        }

    }
}
