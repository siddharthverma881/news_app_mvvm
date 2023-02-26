package com.example.okcreditassignment.ui.news

import android.util.Log
import androidx.lifecycle.*
import com.example.okcreditassignment.model.CommonMLDPojo
import com.example.okcreditassignment.model.news.Article
import com.example.okcreditassignment.model.news.NewsModel
import com.example.okcreditassignment.network.Result
import com.example.okcreditassignment.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

//    private val newsMutableLiveData : MutableLiveData<CommonMLDPojo> = MutableLiveData()
//    val newsLiveData : LiveData<CommonMLDPojo>
//        get() = newsMutableLiveData

    private val articleMutableList : MutableLiveData<CommonMLDPojo> = MutableLiveData()
    val articleLiveData : LiveData<CommonMLDPojo>
    get() = articleMutableList

    private val mutableArticle : MutableLiveData<Article> = MutableLiveData()
    val selectedArticle : LiveData<Article>
        get() = mutableArticle

    fun getHeadlines(map : HashMap<String,String>?, isConnected : Boolean){
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
//        viewModelScope.launch(Dispatchers.IO){
//            val livedata = repository.getAllArticles(map, isConnected)
//            articleMutableList.postValue(livedata.value)
//        }
        CoroutineScope(Dispatchers.IO).launch{
            val livedata = repository.getAllArticles(map, isConnected)
            withContext(Dispatchers.Main) {
                articleMutableList.value = livedata.value
            }
        }
    }

    fun setArticle(article: Article){
        mutableArticle.value = article
    }
}

