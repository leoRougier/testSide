package com.example.testside.config.module

import com.example.testside.service.ToiletService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideGameService(@Named(ApiModule.API_RETROFIT_CACHE) retrofit: Retrofit): ToiletService =
        retrofit.create<ToiletService>(ToiletService::class.java)
}