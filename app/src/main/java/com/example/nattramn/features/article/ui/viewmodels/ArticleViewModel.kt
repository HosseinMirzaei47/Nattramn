package com.example.nattramn.features.article.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.RemoteDataSource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import kotlinx.coroutines.launch
import java.util.*

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository =
        ArticleRepository(RemoteDataSource(), LocalDataSource(application))
    var suggestedArticles = MutableLiveData<ArrayList<ArticleView>>()
    var comments = MutableLiveData<ArrayList<CommentView>>()

    fun setSuggestedArticles() {
        suggestedArticles.value = getArticles()
    }

    fun setComments() {
        comments.value = getComments()
    }

    private fun getArticles(): ArrayList<ArticleView> {

        var articles = ArrayList<ArticleView>()

        viewModelScope.launch {
            articles = articleRepository.getArticles()
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