package com.androiddevs.mvvmnewsapp.util

import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.model.dataClass.CountryCategory

object Constant {
    const val API_KEY="b00c39bb41014789903149b5ce7e6f9c"
    const val BASE_URL="https://newsapi.org/"

    const val ROOM_DATABASE_NAME="NewsRoom.db"
    const val SHARED_PREFERENCES_NAME="pref"
    const val CHOOSE_LANGUAGE="CHOOSE_LANGUAGE"
    const val COUNTRY_NAME_ISO="COUNTRY_NAME_ISO"

    fun countryList():List<CountryCategory>{//Add more country to category
        val countries= arrayListOf<CountryCategory>()
        countries.add(CountryCategory("EGYPT", R.drawable.egypt,"eg"))
        countries.add(CountryCategory("USA", R.drawable.usa,"us"))
        return countries
    }

}