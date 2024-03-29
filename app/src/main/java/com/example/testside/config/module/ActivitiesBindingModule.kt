package com.example.testside.config.module

import com.example.testside.app.map.MapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector
    abstract fun mapActivity () : MapActivity

}