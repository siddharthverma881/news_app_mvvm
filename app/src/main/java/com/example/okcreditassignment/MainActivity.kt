package com.example.okcreditassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.okcreditassignment.di.AppModule
import com.example.okcreditassignment.network.networkModule
import com.example.okcreditassignment.ui.NewsActivity
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        startActivity(Intent(this, NewsActivity::class.java))

    }
}