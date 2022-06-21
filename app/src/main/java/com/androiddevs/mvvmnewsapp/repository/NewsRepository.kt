package com.androiddevs.mvvmnewsapp.repository


import com.androiddevs.mvvmnewsapp.model.dataClass.Article
import com.androiddevs.mvvmnewsapp.model.local.ArticleDao
import com.androiddevs.mvvmnewsapp.model.remote.NewsApi
import javax.inject.Inject


class NewsRepository @Inject constructor(
   private val articleDao: ArticleDao,
   private val api: NewsApi
    ) {
    suspend fun getBreakingNews(countryCode: String,pageNumber: Int)
                = api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuarry:String, pageNumber:Int)
            =api.searchForNews(searchQuarry,pageNumber)

    suspend fun upsert(article: Article)=articleDao.insert(article)

    fun getAllArticles()=articleDao.getAllArticles()

    suspend fun deleteArticle(article: Article)=articleDao.deleteArticle(article)

}