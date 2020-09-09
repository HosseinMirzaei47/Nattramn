package com.example.nattramn.features.user.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.ui.ArticleView
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository = ArticleRepository.getInstance(application)
    var profileArticles = MutableLiveData<ArrayList<ArticleView>>()

    fun setProfileArticles() {
        profileArticles.value = getProfileArticles()
    }

    private fun getProfileArticles(): ArrayList<ArticleView> {

        var profileArticles = ArrayList<ArticleView>()

        viewModelScope.launch {
            profileArticles = articleRepository.getArticlesLocal()
        }

        return profileArticles

    }

}