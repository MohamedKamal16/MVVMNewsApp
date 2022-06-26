package com.androiddevs.mvvmnewsapp.util

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import java.util.*

object LanguageConfig {
     fun setLocate(context: Context,Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val res=context.resources
        val config=res.configuration
        config.setLocale(locale)
        res.updateConfiguration(config ,res.displayMetrics)
    }
}