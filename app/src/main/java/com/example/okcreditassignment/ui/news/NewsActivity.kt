package com.example.okcreditassignment.ui.news

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
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
    private lateinit var mBinding : ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}