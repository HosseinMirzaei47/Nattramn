package com.example.nattramn.features.user.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.MyApp
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActionDialogViewModel : ViewModel() {

    private val profileRepository = ProfileRepository.getInstance(MyApp.app)

    private var _deleteArticleResult = MutableLiveData<Resource<Unit>>()
    val deleteArticleResult: MutableLiveData<Resource<Unit>> get() = _deleteArticleResult

    fun deleteArticle(slug: String) {

        _deleteArticleResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _deleteArticleResult.postValue(profileRepository.deleteArticle(slug))
        }
    }

    fun shareArticle(articleView: ArticleView) {

    }

    fun editArticle(articleView: ArticleView) {

    }

}