package com.example.testside.config.module

import com.example.testside.app.map.mapfragment.MapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun mapFragment () : MapFragment
}