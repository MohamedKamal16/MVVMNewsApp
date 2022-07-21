package com.androiddevs.mvvmnewsapp.ui.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.model.dataClass.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.CountrySelect.getCountryCode
import com.androiddevs.mvvmnewsapp.util.NetworkCheck.hasInternetConnection
import com.androiddevs.mvvmnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AllNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    application: Application,
) : BaseViewModel(application){

    //LiveData
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsResponse: NewsResponse? = null

    fun getBreakNews(context: Context) = viewModelScope.launch {
        safeBrakingNewsCall(getCountryCode(context))
    }




    //handle Breakresponse case success or not
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponce ->
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponce
                } else {
                    val oldArticle = breakingNewsResponse?.articles
                    val newArticle = resultResponce.articles
                    if (newArticle != null) {
                        oldArticle?.clear()
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
                val response = newsRepository.getBreakingNews(countryCode,40)
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