package com.example.testside.config.module

import android.content.Context
import com.example.testside.BuildConfig
import com.example.testside.Interceptor.CacheInterceptor

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File



@Module
class ApiModule {
    companion object {
        const val API_RETROFIT = "API_RETROFIT"
        const val API_RETROFIT_BUILDER = "API_RETROFIT_BUILDER"
        const val API_RETROFIT_CACHE_BUILDER = "API_RETROFIT_CACHE_BUILDER"
        const val API_RETROFIT_CACHE = "API_RETROFIT_CACHE"
    }


    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun prodidecacheInterceptor(context: Context): CacheInterceptor = CacheInterceptor(context)

    @Provides
    @Singleton
    @Named(API_RETROFIT_BUILDER)
    fun provideRetrofitBuilder(httpBuilder: OkHttpClient.Builder,
                               loggingInterceptor: HttpLoggingInterceptor): Retrofit.Builder {
        // Add interceptors
        httpBuilder.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpBuilder.build())
    }

    @Provides
    @Singleton
    @Named(API_RETROFIT)
    fun provideApiRetrofit(@Named(API_RETROFIT_BUILDER) retrofitBuilder: Retrofit.Builder): Retrofit =
        retrofitBuilder.baseUrl("${BuildConfig.BASEURL}").build()

    @Provides
    @Singleton
    @Named(API_RETROFIT_CACHE_BUILDER)
    fun provideCacheRetrofitBuilder(httpBuilder: OkHttpClient.Builder,
                                    loggingInterceptor: HttpLoggingInterceptor,
                                    context: Context,
                                    cacheInterceptor: CacheInterceptor): Retrofit.Builder {

        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        // Add interceptors
        httpBuilder.addInterceptor(loggingInterceptor)
        httpBuilder.addNetworkInterceptor(cacheInterceptor)
        httpBuilder.cache(cache)

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpBuilder.build())
    }

    @Provides
    @Singleton
    @Named(API_RETROFIT_CACHE)
    fun provideApiCacheRetrofit(@Named(API_RETROFIT_CACHE_BUILDER) retrofitBuilder: Retrofit.Builder): Retrofit =
        retrofitBuilder.baseUrl("${BuildConfig.BASEURL}").build()


}