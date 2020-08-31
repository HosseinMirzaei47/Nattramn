package com.example.nattramn.features.user.ui

interface OnProfileArticleListener {

    fun onProfileArticleCardClick(position: Int)

    fun onBookmarkClick(position: Int)

    fun onMoreOptionsClick(position: Int)

    fun onAuthorIconClick(position: Int)

    fun onAuthorNameClick(position: Int)

    fun onArticleCommentsClick(position: Int)

    fun onArticleTitleClick(position: Int)

    fun onArticleDescriptionClick(position: Int)

}