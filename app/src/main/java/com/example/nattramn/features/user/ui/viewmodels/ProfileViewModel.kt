package com.example.nattramn.features.user.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.ProfileRepository
import com.example.nattramn.features.user.ui.UserView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val profileRepository = ProfileRepository.getInstance()
    private val articleRepository = ArticleRepository.getInstance()

    private var _profileResult = MutableLiveData<Resource<UserView>>()
    val profileResult: LiveData<Resource<UserView>> get() = _profileResult

    private var _profileArticlesResult = MutableLiveData<Resource<List<ArticleView>>>()
    val profileArticlesResult: LiveData<Resource<List<ArticleView>>> get() = _profileArticlesResult

    private var _profileBookmarkedArticlesResult = MutableLiveData<Resource<List<ArticleView>>>()
    val profileBookmarkedArticlesResult: LiveData<Resource<List<ArticleView>>> get() = _profileBookmarkedArticlesResult

    private var _bookmarkResult = MutableLiveData<Resource<ArticleView>>()
    val bookmarkResult: LiveData<Resource<ArticleView>> get() = _bookmarkResult

    private var _singleArticleResult = MutableLiveData<Resource<ArticleView>>()
    val singleArticleResult: LiveData<Resource<ArticleView>> get() = _singleArticleResult

    fun bookmarkArticle(slug: String) {

        _bookmarkResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _bookmarkResult.postValue(articleRepository.bookmarkArticle(slug))
        }
    }

    fun getSingleArticle(slug: String) {

        _singleArticleResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _singleArticleResult.postValue(articleRepository.getSingleArticle(slug))
        }

    }

    fun setProfileArticles(username: String) {

        _profileArticlesResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _profileArticlesResult.postValue(profileRepository.getUserArticles(username))
        }
    }

    fun setBookmarkedArticles(username: String) {

        _profileBookmarkedArticlesResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _profileBookmarkedArticlesResult.postValue(
                profileRepository.getBookmarkedArticles(
                    username
                )
            )
        }
    }

    fun setProfile(username: String) {

        _profileResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _profileResult.postValue(profileRepository.getProfile(username))
        }
    }

}