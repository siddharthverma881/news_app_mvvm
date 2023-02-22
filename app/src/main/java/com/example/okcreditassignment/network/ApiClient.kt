package com.example.okcreditassignment.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
//    factory { AuthInterceptor() }
    factory { provideOkHttpClient() }
    factory { apiInterface(get()) }
    single  { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

    return Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())                                   // Kotlin coroutines adapter.
        .addConverterFactory(GsonConverterFactory.create())                                     // GSON builder
        .client(okHttpClient)
        .baseUrl("https://newsapi.org")
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()

     val loggingInterceptor = HttpLoggingInterceptor()
     if(BuildConfig.DEBUG) {
         loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY                                // Logging interceptor
         okHttpClientBuilder.addInterceptor(loggingInterceptor)
     }
    okHttpClientBuilder.protocols(listOf(Protocol.HTTP_1_1))
    okHttpClientBuilder.connectTimeout(40, TimeUnit.SECONDS)                                // connectTimeout
    okHttpClientBuilder.readTimeout(40, TimeUnit.SECONDS)                                   // readTimeout
    okHttpClientBuilder.writeTimeout(40, TimeUnit.SECONDS)

    okHttpClientBuilder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
        val request: Request.Builder = chain.request().newBuilder()
            .header("accept-language", "en")
        chain.proceed(request.build())
    })// readTimeout

    return okHttpClientBuilder.build()
}

fun apiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)





