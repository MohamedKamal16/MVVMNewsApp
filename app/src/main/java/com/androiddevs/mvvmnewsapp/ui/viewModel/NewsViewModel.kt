package com.androiddevs.mvvmnewsapp.ui.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    application: Application,
) : BaseViewModel(application) {
    val news: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsPage = 1
    var newsResponse: NewsResponse? = null


    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {resultResponse->
                newsPage++
                if (newsResponse == null) {
                    newsResponse = resultResponse
                } else {
                    val oldArticle = newsResponse?.articles
                    val newArticle = resultResponse.articles
                    //here check control country and catagory TODO
                    if (newArticle != null ) {
                        oldArticle?.addAll(newArticle)
                    }
                }
                return Resource.Success(newsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeNewsCall(countryCode:String?,category:String?) {
        news.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(context)) {
                //use call method
                val response = newsRepository.getNews(countryCode,category,newsPage)
                //post data in mutable live data
                news.postValue(handleNewsResponse(response))
            } else {
                news.postValue(Resource.Error("NO INTERNET CONNECTION"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> news.postValue(Resource.Error("NETWORK FAILURE"))
                else -> news.postValue(Resource.Error("CONVERSION ERROR"))
            }

        }
    }

    fun getBreakNews(countryCode:String?,category:String?) = viewModelScope.launch {
        safeNewsCall(countryCode,category)
    }

}