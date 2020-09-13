package com.example.nattramn.features.home.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.RemoteDataSource
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.home.data.ArticleHomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val articleHomeRepository =
        ArticleHomeRepository(RemoteDataSource(), LocalDataSource(application))
    var feedArticles = MutableLiveData<ArrayList<ArticleView>>()
    var topArticles = MutableLiveData<ArrayList<ArticleView>>()

    fun setFeedArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            feedArticles.postValue(articleHomeRepository.getArticles())
        }
    }

    fun setTopArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            topArticles.postValue(articleHomeRepository.getArticles())
        }
    }

}