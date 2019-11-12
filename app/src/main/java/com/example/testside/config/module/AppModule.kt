package com.example.testside.config.module

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import com.example.testside.config.App
import android.app.Application
import android.content.Context


@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}