package com.example.testside.config.module

import com.example.testside.service.ToiletCacheService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideCachedToiletService(@Named(ApiModule.API_RETROFIT_CACHE) retrofit: Retrofit): ToiletCacheService =
        retrofit.create<ToiletCacheService>(ToiletCacheService::class.java)

}