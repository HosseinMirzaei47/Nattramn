package com.example.nattramn.features.article.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.RemoteDataSource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.ui.ArticleView
import kotlinx.coroutines.launch
import java.util.*

class TagViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository =
        ArticleRepository(RemoteDataSource(), LocalDataSource(application))
    var tagArticles = MutableLiveData<ArrayList<ArticleView>>()

    fun setTagArticles() {
        tagArticles.value = getTagArticles()
    }

    private fun getTagArticles(): ArrayList<ArticleView> {

        var tagArticles = ArrayList<ArticleView>()

        viewModelScope.launch {
            tagArticles = articleRepository.getArticles()
        }

        return tagArticles

    }

}