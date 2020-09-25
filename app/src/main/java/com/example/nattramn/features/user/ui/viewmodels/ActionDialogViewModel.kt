package com.example.nattramn.features.user.ui.viewmodels

import androidx.lifecycle.ViewModel

class ActionDialogViewModel : ViewModel() {

    /*private val profileRepository = ProfileRepository.getInstance()
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

    }*/

}