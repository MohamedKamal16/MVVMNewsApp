package com.androiddevs.mvvmnewsapp.model.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.mvvmnewsapp.model.local.ArticleDatabase
import com.androiddevs.mvvmnewsapp.util.Constant.ROOM_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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






}