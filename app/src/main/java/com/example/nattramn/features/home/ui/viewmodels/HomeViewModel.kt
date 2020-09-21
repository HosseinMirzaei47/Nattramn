package com.example.nattramn.features.home.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.home.data.ArticleHomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    init {
        /*setLatestArticlesDb()
        setLatestArticles()
        setFeedArticles()*/
    }

    private val articleHomeRepository = ArticleHomeRepository.getInstance()
    private val articleRepository = ArticleRepository.getInstance()

    private var _singleArticleResult = MutableLiveData<Resource<ArticleView>>()
    val singleArticleResult: LiveData<Resource<ArticleView>> get() = _singleArticleResult

    private var _feedResult = MutableLiveData<Resource<List<ArticleView>>>()
    val feedResult: LiveData<Resource<List<ArticleView>>> get() = _feedResult

    private var _latestArticlesResult = MutableLiveData<Resource<List<ArticleView>>>()
    val latestArticlesResult: LiveData<Resource<List<ArticleView>>> get() = _latestArticlesResult

    private var _bookmarkResult = MutableLiveData<Resource<ArticleView>>()
    val bookmarkResult: LiveData<Resource<ArticleView>> get() = _bookmarkResult

    fun setFeedArticles() {

        _feedResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _feedResult.postValue(articleHomeRepository.getFeedArticles())
        }
    }

    fun setLatestArticles() {

        _latestArticlesResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _latestArticlesResult.postValue(articleHomeRepository.getAllArticles())
        }
    }

    fun setLatestArticlesDb() {

        _latestArticlesResult.value = Resource.loading(null)

        _latestArticlesResult.postValue(articleHomeRepository.getAllArticlesDb())
    }

    fun getSingleArticle(slug: String) {

        _singleArticleResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _singleArticleResult.postValue(articleRepository.getSingleArticle(slug))
        }

    }

    fun bookmarkArticle(slug: String) {

        _bookmarkResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _bookmarkResult.postValue(articleRepository.bookmarkArticle(slug))
        }
    }

}