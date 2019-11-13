package com.example.testside.config.module

import com.example.testside.manager.ToiletManager
import com.example.testside.manager.impl.ToiletManagerImpl
import com.example.testside.service.ToiletCacheService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagerModule {

    @Provides
    @Singleton
    fun providesToiletManager(toiletCacheService: ToiletCacheService): ToiletManager = ToiletManagerImpl(toiletCacheService)
}