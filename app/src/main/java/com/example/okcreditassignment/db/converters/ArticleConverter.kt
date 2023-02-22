package com.example.okcreditassignment.db

import androidx.room.TypeConverter
import com.example.okcreditassignment.model.news.Article
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun fromStringArticle(value: String?): Article {
        val listType: Type = object : TypeToken<Article>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayListArticle(list: Article): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<Article> {
        val listType: Type = object : TypeToken<ArrayList<Article>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Article>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}