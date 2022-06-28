package com.androiddevs.mvvmnewsapp.repository


import android.content.Context
import android.content.SharedPreferences
import com.androiddevs.mvvmnewsapp.model.dataClass.Article
import com.androiddevs.mvvmnewsapp.model.local.ArticleDao
import com.androiddevs.mvvmnewsapp.model.remote.NewsApi
import javax.inject.Inject


class NewsRepository @Inject constructor(
   private val articleDao: ArticleDao,
   private val api: NewsApi,
    ) {
     private var countryCode:String?=null

    lateinit var Category:String



    suspend fun getNews(country:String,category:String,pageNumber: Int)
            = api.getNews(country,category, pageNumber)


    suspend fun getBreakingNews(country:String?,pageNumber: Int)
                = api.getBreakingNews(country, pageNumber)

    suspend fun searchNews(searchQuarry:String, pageNumber:Int)
            =api.searchForNews(searchQuarry/*,pageNumber*/)

    suspend fun upsert(article: Article)=articleDao.insert(article)

    fun getAllArticles()=articleDao.getAllArticles()

    suspend fun deleteArticle(article: Article)=articleDao.deleteArticle(article)

}