package com.example.okcreditassignment.db.converters

import androidx.room.TypeConverter
import com.example.okcreditassignment.model.news.Article
import com.example.okcreditassignment.model.news.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SourceConverter {

    @TypeConverter
    fun fromStringSource(value: String?): Source {
        val listType: Type = object : TypeToken<Source>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayListSource(list: Source): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<Source> {
        val listType: Type = object : TypeToken<ArrayList<Source>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Source>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}