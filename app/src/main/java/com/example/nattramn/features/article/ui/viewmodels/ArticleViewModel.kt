package com.example.nattramn.features.article.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.features.article.data.AllArticlesNetwork
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository = ArticleRepository.getInstance(application)
    var suggestedArticles = MutableLiveData<ArrayList<ArticleView>>()
    var comments = MutableLiveData<ArrayList<CommentView>>()
    private var suggested: MutableLiveData<AllArticlesNetwork> =
        MutableLiveData<AllArticlesNetwork>().apply { value = null }

    suspend fun getArticles(forceFetch: Boolean) {

        if (NetworkHelper.isOnline(getApplication())) {
            suggested.postValue(articleRepository.getArticles(forceFetch).value)
        } else {
            setSuggestedArticles()
        }

    }

    fun setSuggestedArticles() {
        suggestedArticles.value = getArticles()
    }

    fun setComments() {
        comments.value = getComments()
    }

    private fun getArticles(): ArrayList<ArticleView> {

        var articles = ArrayList<ArticleView>()

        viewModelScope.launch {
            articles = articleRepository.getArticlesLocal()
        }

        return articles

    }

    private fun getComments(): ArrayList<CommentView> {

        var comments = ArrayList<CommentView>()

        viewModelScope.launch {
            comments = articleRepository.getComments()
        }

        return comments

    }

}