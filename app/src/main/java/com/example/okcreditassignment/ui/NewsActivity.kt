package com.example.okcreditassignment.ui

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.okcreditassignment.R
import com.example.okcreditassignment.appData.Constants
import com.example.okcreditassignment.appData.Utils
import com.example.okcreditassignment.databinding.ActivityNewsBinding
import com.example.okcreditassignment.model.news.Article
import com.example.okcreditassignment.model.news.NewsModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsActivity : AppCompatActivity() {

    private val myViewModel: NewsViewModel by viewModel()
//private lateinit var myViewModel: NewsViewModel
    private lateinit var mBinding : ActivityNewsBinding

    private val networkChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onNetworkChange()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_news)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news)

        mBinding.rvNews.layoutManager = LinearLayoutManager(this)

//        val factory: ViewModelProvider.Factory = ViewModelProvider.NewInstanceFactory()
//        myViewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)


        myViewModel.newsLiveData.observe(this) {
            it?.let {
                if (it.isSuccess) {
                    Log.e("MainActivity", it.data.toString())
                    Utils.logRequestBody(it.data)
                    val newsModel = it.data as NewsModel
                    mBinding.rvNews.adapter = NewsAdapter(this, newsModel.articles!! as ArrayList<Article>)
                }
            }
        }

        myViewModel.articleLiveData.observe(this){
            it?.let {
//                mBinding.rvNews.adapter = NewsAdapter(this, it)
                if(it.isSuccess){
                    if(it.data != null && (it.data as List<Article>) != null && it.data.size > 0){
                        mBinding.rvNews.adapter = NewsAdapter(this, it.data)
                    } else {
                        showAlertDialog("No Articles Found")
                    }
                } else {
                    showAlertDialog(it.message)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(networkChangeReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }

    private fun showAlertDialog(message : String){
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Ok") { _, _ -> }
            .create().show()
    }

    private fun onNetworkChange(){
        HashMap<String, String>().apply {
            this["country"] = "in"
            this["apiKey"] = "6115439f2a3f4eed9d10c4d2e5ca1b90"
            Utils.logRequestBody(this)
            if(Utils.isDeviceOnline(this@NewsActivity)) {
                with(mBinding) {
                    clNetwork.visibility = View.VISIBLE
                    ivNetwork.setBackgroundResource(R.drawable.ic_network_connected)
                    tvNetworkStatus.text = getString(R.string.network_status_online)
                }
                myViewModel.getHeadlines(this, true)
                Handler(Looper.getMainLooper()).
                    postDelayed({
                        mBinding.clNetwork.visibility = View.GONE
                    }, Constants.NETWORK_STATUS_TIMER)

            } else {
                with(mBinding) {
                    clNetwork.visibility = View.VISIBLE
                    ivNetwork.setBackgroundResource(R.drawable.ic_network_disconnected)
                    tvNetworkStatus.text = getString(R.string.network_status_offline)
                }
                myViewModel.getHeadlines(this, false)
            }
        }
    }
}