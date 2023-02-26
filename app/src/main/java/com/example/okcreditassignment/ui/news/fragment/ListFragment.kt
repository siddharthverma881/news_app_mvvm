package com.example.okcreditassignment.ui.news.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.registerReceiver
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.okcreditassignment.R
import com.example.okcreditassignment.appData.Constants
import com.example.okcreditassignment.appData.Utils
import com.example.okcreditassignment.databinding.FragmentListBinding
import com.example.okcreditassignment.model.CommonMLDPojo
import com.example.okcreditassignment.model.news.Article
import com.example.okcreditassignment.model.news.NewsModel
import com.example.okcreditassignment.ui.news.NewsAdapter
import com.example.okcreditassignment.ui.news.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ListFragment : Fragment(), NewsAdapter.ArticleListener {

    private val mViewModel : NewsViewModel by activityViewModel()
    private lateinit var mBinding : FragmentListBinding

    private val networkChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onNetworkChange()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.fragment_list, container, false)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        mBinding.rvNews.layoutManager = LinearLayoutManager(requireContext())

        return mBinding.root
    }

    private var mObserver : Observer<CommonMLDPojo>?= null

    override fun onResume() {
        super.onResume()
        activity?.registerReceiver(networkChangeReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))

        if(!Utils.isDeviceOnline(requireContext())){
            with(mBinding) {
                clNetwork.visibility = View.VISIBLE
                ivNetwork.setBackgroundResource(R.drawable.ic_network_disconnected)
                tvNetworkStatus.text = getString(R.string.network_status_offline)
            }
            mViewModel.getHeadlines(null, false)
        }

        val liveData = mViewModel.articleLiveData
        mObserver = Observer {
            it?.let {
//                mBinding.rvNews.adapter = NewsAdapter(this, it)
                if(it.isSuccess){
                    if(it.data != null && (it.data as List<Article>) != null && it.data.size > 0){
                        mBinding.rvNews.adapter = NewsAdapter(requireContext(), it.data, this@ListFragment)
                    } else {
                        showAlertDialog("No Articles Found")
                    }
                } else {
                    showAlertDialog(it.message)
                }
            }
        }
        liveData.observe(viewLifecycleOwner, mObserver!!)
        /**
        mViewModel.articleLiveData.observe(viewLifecycleOwner){
            it?.let {
//                mBinding.rvNews.adapter = NewsAdapter(this, it)
                if(it.isSuccess){
                    if(it.data != null && (it.data as List<Article>) != null && it.data.size > 0){
                        mBinding.rvNews.adapter = NewsAdapter(requireContext(), it.data)
                    } else {
                        showAlertDialog("No Articles Found")
                    }
                } else {
                    showAlertDialog(it.message)
                }
            }
        }
        */
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(networkChangeReceiver)
    }

    private fun showAlertDialog(message : String){
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Ok") { _, _ -> }
            .create().show()
    }

    private fun onNetworkChange(){
        HashMap<String, String>().apply {
            this["country"] = "in"
            this["apiKey"] = "6115439f2a3f4eed9d10c4d2e5ca1b90"
            Utils.logRequestBody(this)
            if(Utils.isDeviceOnline(requireContext())) {
                with(mBinding) {
                    clNetwork.visibility = View.VISIBLE
                    ivNetwork.setBackgroundResource(R.drawable.ic_network_connected)
                    tvNetworkStatus.text = getString(R.string.network_status_online)
                }
                mViewModel.getHeadlines(this, true)
                Handler(Looper.getMainLooper()).postDelayed({
                    mBinding.clNetwork.visibility = View.GONE
                }, Constants.NETWORK_STATUS_TIMER)

            }
            else {
                with(mBinding) {
                    clNetwork.visibility = View.VISIBLE
                    ivNetwork.setBackgroundResource(R.drawable.ic_network_disconnected)
                    tvNetworkStatus.text = getString(R.string.network_status_offline)
                }
//                myViewModel.getHeadlines(this, false)
            }
        }
    }

    override fun onArticleSelected(article: Article) {
        mViewModel.setArticle(article)
        findNavController().navigate(R.id.action_listFragment_to_detailFragment)
    }
}