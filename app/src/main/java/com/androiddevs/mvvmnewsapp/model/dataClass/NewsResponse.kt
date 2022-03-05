package com.androiddevs.mvvmnewsapp.model.dataClass

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)