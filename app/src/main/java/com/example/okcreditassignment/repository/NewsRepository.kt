package com.example.okcreditassignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.okcreditassignment.appData.Utils
import com.example.okcreditassignment.db.CustomDatabase
import com.example.okcreditassignment.model.CommonMLDPojo
import com.example.okcreditassignment.model.news.Article
import com.example.okcreditassignment.model.news.NewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsRepository(private val database: CustomDatabase) : BaseRepository() {

/*    private val mutableNewsLiveData : MutableLiveData<NewsModel> = MutableLiveData()
    val newsLiveData : LiveData<NewsModel>
        get() = mutableNewsLiveData*/

    private val mutableArticleLiveData : MutableLiveData<CommonMLDPojo> = MutableLiveData()
    val articleLivedata : LiveData<CommonMLDPojo>
        get() = mutableArticleLiveData

    suspend fun getNewsHeadLines(map : Map<String, String>, isConnected : Boolean) : Response<NewsModel> = withContext(Dispatchers.IO) {
        return@withContext apiInterface.getNewsHeadlines(map)
    }

    /*suspend fun getAllArticles(map : Map<String, String>, isConnected : Boolean) {
        if(isConnected) {
            val result = apiInterface.getNewsHeadlines(map)
            if(result.isSuccessful){
                val newsModel = result.body() as NewsModel
                mutableArticleLiveData.postValue(CommonMLDPojo(true, "", newsModel.articles))
            } else {
                mutableArticleLiveData.postValue(CommonMLDPojo(false,result.errorBody().toString(),null))
            }
        }
        else {
            mutableArticleLiveData.postValue(CommonMLDPojo(true, "", database.articleDao.getAllArticles()))
        }
    }*/

    suspend fun getAllArticles(map : Map<String, String>?, isConnected : Boolean) : LiveData<CommonMLDPojo> {
        val mutableLiveData : MutableLiveData<CommonMLDPojo> = MutableLiveData()

        if(isConnected) {
            val result = apiInterface.getNewsHeadlines(map!!)
            if(result.isSuccessful){
                val newsModel = result.body() as NewsModel
                mutableLiveData.postValue(CommonMLDPojo(true, "", newsModel.articles))
            } else {
                mutableLiveData.postValue(CommonMLDPojo(false,result.errorBody().toString(),null))
            }
        }
        else {
            mutableLiveData.postValue(CommonMLDPojo(true, "", database.articleDao.getAllArticles()))
        }
        return mutableLiveData
    }

    suspend fun saveNews(response : CommonMLDPojo){
        val news = response.data as NewsModel
        database.newsDao.addNews(news)
    }

    suspend fun saveArticle(article : Article){
        database.articleDao.addArticle(article)
    }
}