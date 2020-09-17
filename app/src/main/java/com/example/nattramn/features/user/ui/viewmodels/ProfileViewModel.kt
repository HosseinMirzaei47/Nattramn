package com.example.nattramn.features.user.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.ProfileRepository
import com.example.nattramn.features.user.ui.UserView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val profileRepository = ProfileRepository.getInstance(application)

    private var _profileResult = MutableLiveData<Resource<UserView>>()
    val profileResult: LiveData<Resource<UserView>> get() = _profileResult

    private var _profileArticlesResult = MutableLiveData<Resource<List<ArticleView>>>()
    val profileArticlesResult: LiveData<Resource<List<ArticleView>>> get() = _profileArticlesResult

    private var _profileBookmarkedArticlesResult = MutableLiveData<Resource<List<ArticleView>>>()
    val profileBookmarkedArticlesResult: LiveData<Resource<List<ArticleView>>> get() = _profileBookmarkedArticlesResult

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