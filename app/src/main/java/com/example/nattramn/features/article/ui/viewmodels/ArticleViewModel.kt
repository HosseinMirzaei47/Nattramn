package com.example.nattramn.features.article.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.data.models.ArticleComments
import com.example.nattramn.features.article.data.models.Comment
import com.example.nattramn.features.article.data.models.CommentRequest
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository = ArticleRepository.getInstance(application)
    var suggestedArticles = MutableLiveData<ArrayList<ArticleView>>()
    var comments = MutableLiveData<ArrayList<CommentView>>()
    var isBookmarked = MutableLiveData<Boolean>()

    private var _bookmarkResult = MutableLiveData<Resource<ArticleView>>()
    val bookmarkResult: LiveData<Resource<ArticleView>> get() = _bookmarkResult

    private var _sendCommentResult = MutableLiveData<Resource<Unit>>()
    val sendCommentResult: LiveData<Resource<Unit>> get() = _sendCommentResult

    private var _articleCommentsResult = MutableLiveData<Resource<ArticleComments>>()
    val articleCommentsResult: LiveData<Resource<ArticleComments>> get() = _articleCommentsResult

    fun bookmarkArticle(slug: String) {

        _bookmarkResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _bookmarkResult.postValue(articleRepository.bookmarkArticle(slug))
        }
    }

    fun setSuggestedArticles() {
        suggestedArticles.value = getArticles()
    }

    fun sendComment(slug: String, comment: String) {

        val commentRequest = CommentRequest(Comment(comment))

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

    private fun getArticles(): ArrayList<ArticleView> {

        var articles = ArrayList<ArticleView>()

        viewModelScope.launch {
            articles = articleRepository.getArticles()
        }

        return articles

    }

}