package com.androiddevs.mvvmnewsapp.model.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.androiddevs.mvvmnewsapp.model.local.ArticleDatabase
import com.androiddevs.mvvmnewsapp.util.Constant.CHOOSE_LANGUAGE
import com.androiddevs.mvvmnewsapp.util.Constant.ROOM_DATABASE_NAME
import com.androiddevs.mvvmnewsapp.util.Constant.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun provideRunDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ArticleDatabase::class.java,
        ROOM_DATABASE_NAME
    ).build()



    @Singleton
    @Provides
    fun provideRunDao(db: ArticleDatabase) = db.getArticleDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context)=app.getSharedPreferences(SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )




}