package com.androiddevs.mvvmnewsapp.util

import android.content.Context
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.model.dataClass.CountryCategory
import com.androiddevs.mvvmnewsapp.model.dataClass.NewsCategory

object NewsSelect {

    const val NEWS_Category="NEWS_Category"



    fun newsList(context: Context):List<NewsCategory>{//Add more country to category
        val newsCategory= arrayListOf<NewsCategory>()
        newsCategory.add(NewsCategory(context.getString(R.string.business), R.drawable.report,"business"))
        newsCategory.add(NewsCategory(context.getString(R.string.entertainment), R.drawable.digital_campaign,"entertainment"))
       // newsCategory.add(NewsCategory("General", R.drawable.usa,"general"))
        newsCategory.add(NewsCategory(context.getString(R.string.health), R.drawable.healthcare,"health"))
        newsCategory.add(NewsCategory(context.getString(R.string.sports), R.drawable.sports,"sports"))
        newsCategory.add(NewsCategory(context.getString(R.string.science), R.drawable.science,"science"))
        newsCategory.add(NewsCategory(context.getString(R.string.technology), R.drawable.technology,"technology"))
        return newsCategory
    }
}