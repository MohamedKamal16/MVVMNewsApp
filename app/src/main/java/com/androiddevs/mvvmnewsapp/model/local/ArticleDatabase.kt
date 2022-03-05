package com.androiddevs.mvvmnewsapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object{
        @Volatile
        private var instance:ArticleDatabase?=null
        //to synchronize database
        private val LOCk=Any()
        //called when take object from the database
        operator fun invoke(context: Context)= instance ?: synchronized(LOCk){
            instance ?: createDatabase(context).also { instance = it }
        }


        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}