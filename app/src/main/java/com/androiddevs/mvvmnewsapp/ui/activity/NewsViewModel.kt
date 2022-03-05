package com.androiddevs.mvvmnewsapp.ui.activity

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.NewsApplication
import com.androiddevs.mvvmnewsapp.model.dataClass.Article
import com.androiddevs.mvvmnewsapp.model.dataClass.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(val newsRepository: NewsRepository,app:Application):AndroidViewModel(app) {
    //LiveData
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage =1
    var breakingNewsResponse:NewsResponse?=null

    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchgNewsPage =1
    var searchNewsResponse:NewsResponse?=null


    //////////////////////////////
    //not understand 8 video after work on fragment
    init {
        getBreakNews("us")
    }
 //////////////////////////////////////////


    fun getBreakNews(countryCode:String)=viewModelScope.launch {
        safeBrakingNewsCall(countryCode)
    }

    fun searchNews(searchQuary:String)=viewModelScope.launch {
            safeSearchNewsCall(searchQuary)
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //handle Breakresponse case success or not
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponce ->
                ///////////////////////////////////
                //handle pages not understand it video 11
                breakingNewsPage++
                if (breakingNewsResponse==null){
                    breakingNewsResponse=resultResponce
                }else{
                    val oldArticle=breakingNewsResponse?.articles
                    val newArticle=resultResponce.articles
                    oldArticle?.addAll(newArticle)
                }
                //////////////////////////////////////////
                return Resource.Success(breakingNewsResponse?:resultResponce)
            }
        }
        return Resource.Error(response.message())
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
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse?:resultResponce)
            }
        }
        return Resource.Error(response.message())
    }
   //////////////////////////////////////////////////////////////////////////////////////////////

//room methods
    fun saveArticle(article: Article)=viewModelScope.launch {
        newsRepository.upsert(article)
   }

    fun getSavedArticle()=newsRepository.getAllArticles()

    fun deleteArticle(article: Article)=viewModelScope.launch {
      newsRepository.deleteArticle(article)
    }
    /////////////////////////////////////////////////////////////////////////////
    private suspend fun safeBrakingNewsCall(countryCode: String){
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConection()){
                //use call method
                val response=newsRepository.getBreakingNews(countryCode,breakingNewsPage)
                //post data
                breakingNews.postValue(handleBreakingNewsResponse(response))
            }else{
                breakingNews.postValue(Resource.Error("NO INTERNET CONNECTION"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException->breakingNews.postValue(Resource.Error("NETWORK FAILURE"))
                else -> breakingNews.postValue(Resource.Error("CONVERSION ERROR"))
            }

        }
    }

    private suspend fun safeSearchNewsCall(searchQuary: String){
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConection()){
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




    //NetworkCheck
    private fun  hasInternetConection():Boolean{
        val connectivityManager =getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        )as ConnectivityManager
        //check version bigger than api 23
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork =connectivityManager.activeNetwork?:return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI)->true
                capabilities.hasTransport(TRANSPORT_CELLULAR)->true
                capabilities.hasTransport(TRANSPORT_ETHERNET)->true
                else->false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI->true
                    TYPE_MOBILE->true
                    TYPE_ETHERNET->true
                    else->false
                }
            }
        }
        return false
    }
}