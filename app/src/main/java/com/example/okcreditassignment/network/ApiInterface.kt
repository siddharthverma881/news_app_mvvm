package com.example.okcreditassignment.network

import com.example.okcreditassignment.model.news.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET(ApiInventory.GET_NEWS_HEADLINES)
    suspend fun getNewsHeadlines(@QueryMap map: Map<String, String>): Response<NewsModel>
}