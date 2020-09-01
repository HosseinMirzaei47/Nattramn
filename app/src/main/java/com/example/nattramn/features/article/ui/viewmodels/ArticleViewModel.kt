package com.example.nattramn.features.article.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nattramn.core.Utils
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import java.util.*

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    var suggestedArticles = MutableLiveData<ArrayList<ArticleView>>()
    var comments = MutableLiveData<ArrayList<CommentView>>()

    fun setSuggestedArticles() {
        suggestedArticles.value = Utils(getApplication())
            .initArticles()
    }

    fun setComments() {
        comments.value = Utils(getApplication()).initComments()
    }
}