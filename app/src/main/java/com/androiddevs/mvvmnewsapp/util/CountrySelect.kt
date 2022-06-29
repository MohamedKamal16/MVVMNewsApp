package com.androiddevs.mvvmnewsapp.util

import android.content.Context
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.model.dataClass.CountryCategory

object CountrySelect {


    const val COUNTRY_NAME_ISO="COUNTRY_NAME_ISO"

    fun getCountryCode(context: Context):String{
        return  LocaleHelper.getSharedPreference(context).getString(COUNTRY_NAME_ISO,"eg").toString()
    }
    fun countryList(context: Context):List<CountryCategory>{//Add more country to category
        val countries= arrayListOf<CountryCategory>()
        countries.add(CountryCategory(context.getString(R.string.egypt), R.drawable.egypt,"eg"))
        countries.add(CountryCategory(context.getString(R.string.usa), R.drawable.usa,"us"))
        return countries
    }
}