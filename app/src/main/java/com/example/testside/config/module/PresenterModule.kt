package com.example.testside.config.module

import com.example.testside.app.map.mapfragment.MapFragmentPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideMapPresenter() = MapFragmentPresenter()
}