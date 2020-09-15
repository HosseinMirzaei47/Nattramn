package com.example.nattramn.features.home.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.home.data.ArticleHomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val articleHomeRepository = ArticleHomeRepository.getInstance(application)
    var feedArticles = MutableLiveData<ArrayList<ArticleView>>()
    var topArticles = MutableLiveData<ArrayList<ArticleView>>()
    private var _feedResult = MutableLiveData<Resource<List<ArticleView>>>()
    val feedResult: LiveData<Resource<List<ArticleView>>> get() = _feedResult

    fun setFeedArticles() {

        _feedResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _feedResult.postValue(articleHomeRepository.getFeedArticles())
        }
    }

    fun setTopArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            topArticles.postValue(articleHomeRepository.getArticles())
        }
    }

}