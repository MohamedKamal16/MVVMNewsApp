package com.androiddevs.mvvmnewsapp.model.local.typeConverter

import androidx.room.TypeConverter
import com.androiddevs.mvvmnewsapp.model.dataClass.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    companion object{
        //to insert weather in table as gson
        @TypeConverter
        @JvmStatic
        fun fromSource(value: Source): String {
            val type = object : TypeToken<Source>() {}.type
            return Gson().toJson(value, type)
        }
        //to take it out from table as list of weather
        @TypeConverter
        @JvmStatic
        fun toSource(value: String): Source {
            val type = object : TypeToken<Source>() {}.type
            return Gson().fromJson(value, type)
        }
    }
}
