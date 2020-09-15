package com.example.nattramn.features.user.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.features.article.data.ArticleRepository
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.AuthRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository =
        ArticleRepository(AuthRemoteDataSource(), LocalDataSource(application))
    var profileArticles = MutableLiveData<ArrayList<ArticleView>>()

    fun setProfileArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            profileArticles.postValue(articleRepository.getArticles())
        }
    }

}