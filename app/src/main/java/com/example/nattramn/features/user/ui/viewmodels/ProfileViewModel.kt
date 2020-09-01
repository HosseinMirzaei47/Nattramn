package com.example.nattramn.features.user.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nattramn.core.Utils
import com.example.nattramn.features.article.ui.ArticleView
import java.util.*

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    var profileArticles = MutableLiveData<ArrayList<ArticleView>>()

    fun setProfileArticles() {
        profileArticles.value = Utils(getApplication())
            .initArticles()
    }
}