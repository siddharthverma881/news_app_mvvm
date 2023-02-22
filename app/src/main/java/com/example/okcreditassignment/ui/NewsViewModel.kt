package com.example.okcreditassignment.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.okcreditassignment.model.CommonMLDPojo
import com.example.okcreditassignment.model.news.Article
import com.example.okcreditassignment.model.news.NewsModel
import com.example.okcreditassignment.network.Result
import com.example.okcreditassignment.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val newsMutableLiveData : MutableLiveData<CommonMLDPojo> = MutableLiveData()
    val newsLiveData : LiveData<CommonMLDPojo>
        get() = newsMutableLiveData

    private val articleMutableList : MutableLiveData<CommonMLDPojo> = MutableLiveData()
    val articleLiveData : LiveData<CommonMLDPojo>
    get() = articleMutableList

    fun getHeadlines(map : HashMap<String,String>, isConnected : Boolean){
        /*viewModelScope(Dispatchers.IO).launch {
            val result = repository.getNewsHeadLines(map, isConnected)
            if(result.isSuccessful){
                Log.e("ViewModel", "Success")
                val response = CommonMLDPojo(true, "", result.body())
                newsMutableLiveData.value = response
                repository.saveNews(response)
                val tempResponse = response.data as NewsModel
                repository.saveArticle(tempResponse.articles!![0])
            } else {
                Log.e("ViewModel", "Error")
            }
        }*/

        CoroutineScope(Dispatchers.IO).launch{
            repository.getAllArticles(map, isConnected)
        }
        repository.articleLivedata.observeForever {
//            it?.let {
//                articleMutableList.value = it
//            }
            articleMutableList.value = it
        }
    }
}

