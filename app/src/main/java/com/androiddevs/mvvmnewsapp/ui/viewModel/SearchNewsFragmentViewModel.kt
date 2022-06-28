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
class SearchNewsFragmentViewModel@Inject constructor(private val newsRepository: NewsRepository, application: Application,):BaseViewModel(application) {
    //LiveData
    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchgNewsPage =1
    var searchNewsResponse:NewsResponse?=null

    fun searchNews(searchQuary:String)=viewModelScope.launch {
            safeSearchNewsCall(searchQuary)
    }

    //handle search response case success or not
    private fun handleSearchNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {resultResponce ->
                searchgNewsPage++
                if (searchNewsResponse==null){
                    searchNewsResponse==resultResponce
                }else{
                    val oldArticle=searchNewsResponse?.articles
                    val newArticle=resultResponce.articles
                    if (newArticle != null) {
                        oldArticle?.addAll(newArticle)
                    }
                }
                return Resource.Success(searchNewsResponse?:resultResponce)
            }
        }
        return Resource.Error(response.message())
    }


    private suspend fun safeSearchNewsCall(searchQuary: String){
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(context)){
                //use call method
                val response=newsRepository.searchNews(searchQuary,searchgNewsPage)
                //post data
                searchNews.postValue(handleSearchNewsResponse(response))
            }else{
                searchNews.postValue(Resource.Error("NO INTERNET CONNECTION"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException->searchNews.postValue(Resource.Error("NETWORK FAILURE"))
                else -> searchNews.postValue(Resource.Error("CONVERSION ERROR"))
            }

        }
    }






}