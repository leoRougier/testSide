package com.example.testside.config

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App: DaggerApplication() {

    companion object {
        @JvmStatic
        lateinit var component: AppComponent
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent
        .builder()
        .create(this)
        .build()
}