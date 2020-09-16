package com.example.nattramn.features.article.ui

interface OnArticleListener {

    fun onCardClick(slug: String)

    fun onArticleTitleClick(slug: String)

    fun onArticleSaveClick(position: Int)

    fun onAuthorNameClick(position: Int)

    fun onAuthorIconClick(position: Int)

}