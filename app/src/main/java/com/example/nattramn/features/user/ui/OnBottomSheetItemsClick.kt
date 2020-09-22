package com.example.nattramn.features.user.ui

interface OnBottomSheetItemsClick {

    fun onShareArticle(action: String)

    fun onDeleteArticle(action: String)

    fun onEditArticle(action: String, slug: String)

}