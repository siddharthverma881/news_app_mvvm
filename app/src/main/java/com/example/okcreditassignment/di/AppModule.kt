package com.example.okcreditassignment.di

import android.app.Application
import androidx.room.Room
import com.example.okcreditassignment.db.dao.ArticleDao
import com.example.okcreditassignment.db.dao.NewsDao
import com.example.okcreditassignment.db.CustomDatabase
import com.example.okcreditassignment.repository.NewsRepository
import com.example.okcreditassignment.ui.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    val newsDb = module {
        fun provideDataBase(application: Application): CustomDatabase {
            return Room.databaseBuilder(application, CustomDatabase::class.java, "news_database")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun provideNewsDao(dataBase: CustomDatabase): NewsDao {
            return dataBase.newsDao
        }

        fun provideArticleDao(database: CustomDatabase): ArticleDao {
            return database.articleDao
        }
        single { provideDataBase(androidApplication()) }
        single { provideNewsDao(get()) }
        single { provideArticleDao(get())}
    }

    val viewModels = module {
        factory { NewsRepository(get()) }
        viewModel { NewsViewModel(get()) }
    }
}