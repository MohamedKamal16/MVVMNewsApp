package com.androiddevs.mvvmnewsapp.util

import android.content.Context
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.model.dataClass.CountryCategory
import com.androiddevs.mvvmnewsapp.util.LocaleHelper.getSharedPreference

object Constant {
    /*
    096f78e0d8d744c5b75aee14daccff3b
   b00c39bb41014789903149b5ce7e6f9c
     */
    const val API_KEY="096f78e0d8d744c5b75aee14daccff3b"
    const val BASE_URL="https://newsapi.org/"

    const val ROOM_DATABASE_NAME="NewsRoom.db"



    const val COUNTRY_NAME_ISO="COUNTRY_NAME_ISO"
    fun getCountryCode(context:Context):String{
      return  getSharedPreference(context).getString(COUNTRY_NAME_ISO,"eg").toString()
    }
    fun countryList():List<CountryCategory>{//Add more country to category
        val countries= arrayListOf<CountryCategory>()
        countries.add(CountryCategory("EGYPT", R.drawable.egypt,"eg"))
        countries.add(CountryCategory("USA", R.drawable.usa,"us"))
        return countries
    }

}