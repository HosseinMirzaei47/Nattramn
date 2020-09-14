package com.example.nattramn.features.article.data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.nattramn.core.LocalDataSource
import com.example.nattramn.core.MyApis
import com.example.nattramn.core.RemoteDataSource
import com.example.nattramn.core.ServiceBuilder
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticleRepository(
    private val remoteDataSource: RemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    private var allArticlesRemote: MutableLiveData<AllArticlesNetwork> =
        MutableLiveData<AllArticlesNetwork>().apply { value = null }

    companion object {

        private const val TAG = "jalil"
        private var myInstance: ArticleRepository? = null

        fun getInstance(application: Application): ArticleRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = ArticleRepository(
                        RemoteDataSource(), LocalDataSource(
                            application
                        )
                    )
                }
            }
            return myInstance!!
        }
    }

    suspend fun getArticles(forceFetch: Boolean): MutableLiveData<AllArticlesNetwork> {

        if (allArticlesRemote.value!!.articles.isNotEmpty() && !forceFetch) {
            return allArticlesRemote
        } else if (forceFetch) {
            getArticlesLocal()
        } else {
            GlobalScope.launch {

                val getArticlesResponse = safeApiCall {
                    ServiceBuilder.buildService(MyApis::class.java).getArticles()
                }
                allArticlesRemote.postValue(getArticlesResponse)

            }
        }

        return allArticlesRemote
    }

    suspend fun getArticlesLocal(): ArrayList<ArticleView> {
        return localDataSource.getArticles()
    }

    suspend fun getComments(): ArrayList<CommentView> {
        return localDataSource.getComments()
    }

    private suspend inline fun <T> safeApiCall(responseFunction: () -> T): T? {
        return try {
            responseFunction.invoke()//Or responseFunction()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}