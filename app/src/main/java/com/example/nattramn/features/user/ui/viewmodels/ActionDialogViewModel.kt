package com.example.nattramn.features.user.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ActionDialogViewModel : ViewModel() {

    private val profileRepository = ProfileRepository.getInstance()
    private val articleRepository = ArticleRepository.getInstance()

    private var _deleteArticleResult = MutableLiveData<Resource<Response<Unit>??>>()
    val deleteArticleResult: LiveData<Resource<Response<Unit>?>> get() = _deleteArticleResult

    private var _singleArticleResult = MutableLiveData<Resource<ArticleView>>()
    val singleArticleResult: MutableLiveData<Resource<ArticleView>> get() = _singleArticleResult

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

    fun shareArticle(articleView: ArticleView) {

    }

    fun editArticle(articleView: ArticleView) {

    }

}