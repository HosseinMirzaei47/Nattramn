package com.example.nattramn.features.article.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.data.models.ArticleComments
import com.example.nattramn.features.article.data.models.CommentRequest
import com.example.nattramn.features.article.data.models.SingleCommentRequest
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val articleRepository = ArticleRepository.getInstance()
    var comments = MutableLiveData<ArrayList<CommentView>>()

    private var _bookmarkResult = MutableLiveData<Resource<ArticleView>>()
    val bookmarkResult: LiveData<Resource<ArticleView>> get() = _bookmarkResult

    private var _sendCommentResult = MutableLiveData<Resource<Unit>>()
    val sendCommentResult: LiveData<Resource<Unit>> get() = _sendCommentResult

    private var _articleCommentsResult = MutableLiveData<Resource<ArticleComments>>()
    val articleCommentsResult: LiveData<Resource<ArticleComments>> get() = _articleCommentsResult

    private var _tagArticlesResult = MutableLiveData<Resource<List<ArticleView>>>()
    val tagArticlesResult: LiveData<Resource<List<ArticleView>>> get() = _tagArticlesResult

    private var _singleArticleResult = MutableLiveData<Resource<ArticleView>>()
    val singleArticleResult: LiveData<Resource<ArticleView>> get() = _singleArticleResult

    fun bookmarkArticle(slug: String) {

        _bookmarkResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _bookmarkResult.postValue(articleRepository.bookmarkArticle(slug))
        }
    }

    fun sendComment(slug: String, comment: String) {

        val commentRequest = CommentRequest(SingleCommentRequest(comment))

        _sendCommentResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _sendCommentResult.postValue(articleRepository.sendComment(slug, commentRequest))
        }
    }

    fun getArticleComments(slug: String) {

        _articleCommentsResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _articleCommentsResult.postValue(articleRepository.getArticleComments(slug))
        }
    }

    fun getTagArticles(tag: String) {

        _tagArticlesResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _tagArticlesResult.postValue(articleRepository.getTagArticles(tag))
        }
    }

    fun getSingleArticle(slug: String) {

        _singleArticleResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _singleArticleResult.postValue(articleRepository.getSingleArticle(slug))
        }

    }

}