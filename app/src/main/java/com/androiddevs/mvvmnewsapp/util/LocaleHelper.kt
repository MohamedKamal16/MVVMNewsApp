package com.androiddevs.mvvmnewsapp.util

import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import java.util.*


object LocaleHelper {
    //control language code
    fun languageList(): Array<String> {//fun that add language to dialog
        return arrayOf("English", "عربي")
    }
    fun localLanguageCode(index:Int):String{
        val language =when(index){
            0->"en"
            1->"ar"
            else->"en"
        }
        return language
    }
    fun returnLanguageIndex(context: Context):Int{
    return when(getLanguage(context)){
        "ar"->1
        "en"->0
        else->-1
        }
    }


    //SharedPreference Code
    const val SHARED_PREFERENCES_NAME="pref"
    const val CHOOSE_LANGUAGE="CHOOSE_LANGUAGE"
    fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun onAttach(context: Context): Context? {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

   fun onAttach(context: Context, defaultLanguage: String): Context {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String? {
        return getPersistedData(context, Locale.getDefault().language)
    }


    fun setLocale(context: Context, language: String?): Context {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String? {
        val preferences =context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        return preferences.getString(CHOOSE_LANGUAGE, defaultLanguage)
    }


    private fun persist(context: Context, language: String?) {
        val preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(CHOOSE_LANGUAGE, language)
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String?): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String?): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}