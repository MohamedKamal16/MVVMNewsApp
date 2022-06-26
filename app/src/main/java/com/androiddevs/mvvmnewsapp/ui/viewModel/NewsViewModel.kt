package com.androiddevs.mvvmnewsapp.ui.viewModel

import android.app.Application
import android.content.SharedPreferences
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.model.dataClass.Article
import com.androiddevs.mvvmnewsapp.model.dataClass.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Constant.COUNTRY_NAME_ISO
import com.androiddevs.mvvmnewsapp.util.NetworkCheck.hasInternetConnection
import com.androiddevs.mvvmnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    application: Application,
) : BaseViewModel(application) {
    //LiveData
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null




    fun getBreakNews(countryCode:String?) = viewModelScope.launch {
        safeBrakingNewsCall(countryCode)
    }


    //handle Breakresponse case success or not
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponce ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponce
                } else {
                    val oldArticle = breakingNewsResponse?.articles
                    val newArticle = resultResponce.articles
                    if (newArticle != null) {
                        oldArticle?.addAll(newArticle)
                    }
                }
                return Resource.Success(breakingNewsResponse ?: resultResponce)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeBrakingNewsCall(countryCode:String?) {
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(context)) {
                //use call method
                val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
                //post data in mutable live data
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("NO INTERNET CONNECTION"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> breakingNews.postValue(Resource.Error("NETWORK FAILURE"))
                else -> breakingNews.postValue(Resource.Error("CONVERSION ERROR"))
            }

        }
    }

}