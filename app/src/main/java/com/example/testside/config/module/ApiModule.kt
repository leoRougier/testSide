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
import android.net.NetworkInfo
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import okhttp3.CacheControl
import okhttp3.Interceptor


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

   /* private fun provideCache(): Cache? {
        var cache: Cache? = null

        try {
            cache = Cache(
                File(context.getCacheDir(), "http-cache"),
                10 * 1024 * 1024
            ) // 10 MB
        } catch (e: Exception) {
            //Log.e(TAG, "Could not create Cache!")
        }

        return cache
    }*/

   /* private fun provideCacheInterceptor(): Interceptor {
        return { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl: CacheControl

            if (isConnected()) {
                cacheControl = CacheControl.Builder()
                    .maxAge(0, TimeUnit.SECONDS)
                    .build()
            } else {
                cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()
            }

            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()

        }
    }

    private fun provideOfflineCacheInterceptor(): Interceptor {
        return { chain ->
            var request = chain.request()

            if (!isConnected()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }

            chain.proceed(request)
        }
    }*/

   /* fun isConnected(): Boolean {
        try {
            val e = mContext.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as android.net.ConnectivityManager
            val activeNetwork = e.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        } catch (e: Exception) {
            Log.w(TAG, e.toString())
        }

        return false
    }*/
}