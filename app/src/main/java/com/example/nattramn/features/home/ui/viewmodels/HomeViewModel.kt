package com.example.nattramn.features.home.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nattramn.core.Utils
import com.example.nattramn.models.Article
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var feedArticles = MutableLiveData<ArrayList<Article>>()
    var topArticles = MutableLiveData<ArrayList<Article>>()

    fun setFeedArticles() {
        feedArticles.value = Utils(getApplication()).initArticles()
    }

    fun setTopArticles() {
        topArticles.value = Utils(getApplication()).initArticles()
    }
}