package com.example.nattramn.features.article.ui

interface OnArticleListener {

    fun onCardClick(slug: String)

    fun onArticleTitleClick(slug: String)

    fun onArticleSaveClick(slug: String)

    fun onAuthorNameClick(slug: String)

    fun onAuthorIconClick(slug: String)

}