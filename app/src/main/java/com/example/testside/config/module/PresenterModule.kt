package com.example.testside.config.module

import com.example.testside.app.map.listfragment.ListFragmentPresenter
import com.example.testside.app.map.mapfragment.MapFragmentPresenter
import com.example.testside.manager.ToiletManager
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideMapPresenter(toiletManager: ToiletManager) = MapFragmentPresenter(toiletManager)

    @Provides
    fun provideListFragmentPresenter(toiletManager: ToiletManager) = ListFragmentPresenter(toiletManager)
}