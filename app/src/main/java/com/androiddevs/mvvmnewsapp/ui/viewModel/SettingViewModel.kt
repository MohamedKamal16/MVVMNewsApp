package com.androiddevs.mvvmnewsapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel@Inject constructor(private val newsRepository: NewsRepository): ViewModel() {
   /* fun changeLanguage(context: Context)=newsRepository.changeLanguage(context)
    fun getLocal():String= newsRepository.getLocal()*/
}