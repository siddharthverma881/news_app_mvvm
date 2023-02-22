package com.example.okcreditassignment.model.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity("news_table")
data class NewsModel(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var status : String?= null,
    var totalResults: Int?= null,
    var articles: ArrayList<Article>?= null
)