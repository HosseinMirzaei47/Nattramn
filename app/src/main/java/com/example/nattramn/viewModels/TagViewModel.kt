package com.example.nattramn.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nattramn.Utils
import com.example.nattramn.models.Article
import java.util.*

class TagViewModel(application: Application) : AndroidViewModel(application) {

    var tagArticles = MutableLiveData<ArrayList<Article>>()

    fun setTagArticles() {
        tagArticles.value = Utils(getApplication()).initArticles()
    }
}