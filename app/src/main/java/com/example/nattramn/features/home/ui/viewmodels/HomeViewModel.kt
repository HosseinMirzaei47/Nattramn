package com.example.nattramn.features.home.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.RemoteDataSource
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.home.data.ArticleHomeRepository
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val articleHomeRepository =
        ArticleHomeRepository(RemoteDataSource(), LocalDataSource(application))
    var feedArticles = MutableLiveData<ArrayList<ArticleView>>()
    var topArticles = MutableLiveData<ArrayList<ArticleView>>()

    fun setFeedArticles() {
        feedArticles.value = getFeedArticles()
    }

    fun setTopArticles() {
        topArticles.value = getTopArticles()
    }

    private fun getFeedArticles(): ArrayList<ArticleView> {

        var feedArticles = ArrayList<ArticleView>()

        viewModelScope.launch {
            feedArticles = articleHomeRepository.getArticles()
        }

        return feedArticles

    }

    private fun getTopArticles(): ArrayList<ArticleView> {

        var topArticles = ArrayList<ArticleView>()

        viewModelScope.launch {
            topArticles = articleHomeRepository.getArticles()
        }

        return topArticles

    }
}