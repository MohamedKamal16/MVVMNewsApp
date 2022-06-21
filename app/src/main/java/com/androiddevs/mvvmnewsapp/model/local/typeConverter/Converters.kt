package com.androiddevs.mvvmnewsapp.model.local.typeConverter

import androidx.room.TypeConverter
import com.androiddevs.mvvmnewsapp.model.dataClass.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source):String?{
        return source.name
    }
    @TypeConverter
    fun toSource(name: String):Source{
        return Source(name,name)
    }
}