package com.example.okcreditassignment

import android.app.Application
import com.example.okcreditassignment.di.AppModule
import com.example.okcreditassignment.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(
                AppModule.newsDb,
                AppModule.viewModels,
                networkModule
            )
        }
    }
}