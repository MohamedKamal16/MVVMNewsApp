package com.androiddevs.mvvmnewsapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkCheck {

     fun hasInternetConnection(context: Context):Boolean{
        val connectivityManager =context.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager
        //check version bigger than api 23
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork =connectivityManager.activeNetwork?:return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
                else->false
            }
        } else{//check other versions
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI ->true
                    ConnectivityManager.TYPE_MOBILE ->true
                    ConnectivityManager.TYPE_ETHERNET ->true
                    else->false
                }
            }
        }
        return false
    }
}