package com.example.nattramn.features.article.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.data.models.EditArticleRequest
import com.example.nattramn.features.article.ui.ArticleView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteViewModel : ViewModel() {

    private val articleRepository = ArticleRepository.getInstance()

    private var _editArticleResult = MutableLiveData<Resource<ArticleView>>()
    val editArticleResult: LiveData<Resource<ArticleView>> get() = _editArticleResult

    private var _createArticleResult = MutableLiveData<Resource<ArticleView>>()
    val createArticleResult: LiveData<Resource<ArticleView>> get() = _createArticleResult

    fun createArticle(slug: String) {

    }

    fun editArticle(editArticleRequest: EditArticleRequest) {

        _editArticleResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _editArticleResult.postValue(articleRepository.editArticle(editArticleRequest))
        }
    }

}