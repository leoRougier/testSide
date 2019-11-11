package com.example.testside.config.module

import com.example.testside.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Module
    class ApiModule {
        companion object {
            const val API_RETROFIT = "API_RETROFIT"
            const val API_RETROFIT_BUILDER = "API_RETROFIT_BUILDER"
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
            httpLoggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return httpLoggingInterceptor
        }

        @Provides
        @Singleton
        @Named(API_RETROFIT_BUILDER)
        fun provideOauthRetrofitBuilder(
            httpBuilder: OkHttpClient.Builder,
            loggingInterceptor: HttpLoggingInterceptor
        ): Retrofit.Builder {
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


    }
}