package com.example.nattramn.recyclerItemListeners

interface OnArticleListener {

    fun onCardClick(position: Int)

    fun onArticleSaveClick(position: Int)

    fun onAuthorNameClick(position: Int)

    fun onAuthorIconClick(position: Int)

    fun onArticleTitleClick(position: Int)

}