package com.androiddevs.mvvmnewsapp.repository


import com.androiddevs.mvvmnewsapp.model.dataClass.Article
import com.androiddevs.mvvmnewsapp.model.local.ArticleDatabase
import com.androiddevs.mvvmnewsapp.model.remote.RetrofitInstance


class NewsRepository(
    val db:ArticleDatabase
    ) {
    suspend fun getBreakingNews(countryCode: String,pageNumber: Int)
                = RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuarry:String, pageNumber:Int)
            =RetrofitInstance.api.searchForNews(searchQuarry,pageNumber)

    suspend fun upsert(article: Article)=db.getArticleDao().upsert(article)

    fun getAllArticles()=db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article)=db.getArticleDao().deleteArticle(article)

}