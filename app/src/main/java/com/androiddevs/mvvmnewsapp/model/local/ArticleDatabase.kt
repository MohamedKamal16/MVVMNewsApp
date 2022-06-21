package com.androiddevs.mvvmnewsapp.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.model.dataClass.Article
import com.androiddevs.mvvmnewsapp.model.local.typeConverter.Converters

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase :RoomDatabase() {
    abstract fun  getArticleDao():ArticleDao
}