package com.example.nattramn.features.user.ui

interface OnProfileArticleListener {

    fun onProfileArticleCardClick(slug: String)

    fun onBookmarkClick(slug: String)

    fun onMoreOptionsClick(slug: String)

    fun onAuthorIconClick(username: String)

    fun onAuthorNameClick(username: String)

    fun onArticleCommentsClick(position: Int)

    fun onArticleTitleClick(slug: String)

    fun onArticleDescriptionClick(slug: String)

}