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
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val profileRepository = ProfileRepository.getInstance()
    private val articleRepository = ArticleRepository.getInstance()

    private var _profileResult = MutableLiveData<Resource<UserView>>()
    val profileResult: LiveData<Resource<UserView>> get() = _profileResult

    private var _userArticlesResult = MutableLiveData<Resource<List<ArticleView>>>()
    val userArticlesResult: LiveData<Resource<List<ArticleView>>> get() = _userArticlesResult

    private var _profileBookmarkedArticlesResult = MutableLiveData<Resource<List<ArticleView>>>()
    val profileBookmarkedArticlesResult: LiveData<Resource<List<ArticleView>>> get() = _profileBookmarkedArticlesResult

    private var _bookmarkResult = MutableLiveData<Resource<ArticleView>>()
    val bookmarkResult: LiveData<Resource<ArticleView>> get() = _bookmarkResult

    private var _singleArticleResult = MutableLiveData<Resource<ArticleView>>()
    val singleArticleResult: LiveData<Resource<ArticleView>> get() = _singleArticleResult

    private var _deleteArticleResult = MutableLiveData<Resource<Response<Unit>?>>()
    val deleteArticleResult: LiveData<Resource<Response<Unit>?>> get() = _deleteArticleResult

    fun getUserDb(username: String) = profileRepository.getUserDb(username)

    fun getUserArticlesDb(username: String) = profileRepository.getUserArticlesDb(username)

    fun getSingleArticle(slug: String) {

        _singleArticleResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _singleArticleResult.postValue(articleRepository.getSingleArticle(slug))
        }
    }

    fun deleteArticle(slug: String) {

        _deleteArticleResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _deleteArticleResult.postValue(profileRepository.deleteArticle(slug))
        }
    }

    fun bookmarkArticle(slug: String) {

        _bookmarkResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _bookmarkResult.postValue(articleRepository.bookmarkArticle(slug))
        }
    }

    fun getUserArticles(username: String) {

        _userArticlesResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _userArticlesResult.postValue(profileRepository.getUserArticles(username))
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