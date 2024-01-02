package com.kamford.app.di

import android.annotation.SuppressLint
import android.content.Context
import com.kamford.app.di.Constants.API_BASE_URL
import com.kamford.app.di.Constants.API_KEY
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

object NetworkModule {

    lateinit var okHttpClient: OkHttpClient
    lateinit var moshi: Moshi
    lateinit var retrofit: Retrofit

    val kamfordApi by lazy {
        retrofit.create(KamfordServiceApi::class.java)
    }

    val provideAuthInterceptor =  Interceptor { chain: Interceptor.Chain ->
        val initialRequest = chain.request()
        val newUrl = initialRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("android_id", RoomModule.androidFinger)
            .addQueryParameter("token", RoomModule.tokenKey)
            .build()
        val newRequest = initialRequest.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }

    @SuppressLint("HardwareIds")
    fun provide(context: Context) {
        okHttpClient = OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http_cache"), (20 * 1024 * 1024).toLong()))
            .apply {eventListenerFactory(LoggingEventListener.Factory())}
            .addInterceptor(provideAuthInterceptor)
            .build()

        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }
}