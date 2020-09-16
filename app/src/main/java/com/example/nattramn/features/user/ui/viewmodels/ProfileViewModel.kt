package com.example.nattramn.features.user.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val profileRepository = ProfileRepository.getInstance(application)
    var profileArticles = MutableLiveData<ArrayList<ArticleView>>()
    private var _profileArticlesResult = MutableLiveData<Resource<List<ArticleView>>>()
    val profileArticlesResult: LiveData<Resource<List<ArticleView>>> get() = _profileArticlesResult

    fun setProfileArticles(username: String) {

        _profileArticlesResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _profileArticlesResult.postValue(profileRepository.getUserArticles(username))
        }
    }

}