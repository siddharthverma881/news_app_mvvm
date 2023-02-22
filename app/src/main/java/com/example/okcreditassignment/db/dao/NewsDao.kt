package com.example.okcreditassignment.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.okcreditassignment.model.news.Article
import com.example.okcreditassignment.model.news.NewsModel

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(newsModel : NewsModel)

    @Query("SELECT * FROM news_table")
    fun getAllNews() : LiveData<NewsModel>
}