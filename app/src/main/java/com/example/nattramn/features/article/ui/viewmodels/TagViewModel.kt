package com.example.nattramn.features.article.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nattramn.core.Utils
import com.example.nattramn.models.Article
import java.util.*

class TagViewModel(application: Application) : AndroidViewModel(application) {

    var tagArticles = MutableLiveData<ArrayList<Article>>()

    fun setTagArticles() {
        tagArticles.value = Utils(getApplication()).initArticles()
    }
}