package com.example.nattramn.features.article.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nattramn.core.Utils
import com.example.nattramn.models.Article
import com.example.nattramn.models.Comment
import java.util.*

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    var suggestedArticles = MutableLiveData<ArrayList<Article>>()
    var comments = MutableLiveData<ArrayList<Comment>>()

    fun setSuggestedArticles() {
        suggestedArticles.value = Utils(getApplication())
            .initArticles()
    }

    fun setComments() {
        comments.value = Utils(getApplication()).initComments()
    }
}