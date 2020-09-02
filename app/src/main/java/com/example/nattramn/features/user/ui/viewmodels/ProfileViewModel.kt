package com.example.nattramn.features.user.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.RemoteDataSource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.ui.ArticleView
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository =
        ArticleRepository(RemoteDataSource(), LocalDataSource(application))
    var profileArticles = MutableLiveData<ArrayList<ArticleView>>()

    fun setProfileArticles() {
        profileArticles.value = getProfileArticles()
    }

    private fun getProfileArticles(): ArrayList<ArticleView> {

        var profileArticles = ArrayList<ArticleView>()

        viewModelScope.launch {
            profileArticles = articleRepository.getArticles()
        }

        return profileArticles

    }

}