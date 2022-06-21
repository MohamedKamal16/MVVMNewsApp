package com.androiddevs.mvvmnewsapp.ui.viewModel

import android.app.Application
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.model.dataClass.Article
import com.androiddevs.mvvmnewsapp.model.dataClass.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.NetworkCheck.hasInternetConnection
import com.androiddevs.mvvmnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SavedNewsFragmentViewModel@Inject constructor(private val newsRepository: NewsRepository, application: Application,):BaseViewModel(application) {

//room methods
    fun saveArticle(article: Article)=viewModelScope.launch {
        newsRepository.upsert(article)
   }

    fun getSavedArticle()=newsRepository.getAllArticles()

    fun deleteArticle(article: Article)=viewModelScope.launch {
      newsRepository.deleteArticle(article)
    }









}