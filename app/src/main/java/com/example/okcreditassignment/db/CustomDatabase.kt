package com.example.okcreditassignment.db

import androidx.room.*
import com.example.okcreditassignment.db.converters.SourceConverter
import com.example.okcreditassignment.db.dao.ArticleDao
import com.example.okcreditassignment.db.dao.NewsDao
import com.example.okcreditassignment.model.news.Article
import com.example.okcreditassignment.model.news.NewsModel

@Database(entities = [NewsModel::class, Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class, SourceConverter::class)
abstract class CustomDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
    abstract val articleDao : ArticleDao
}