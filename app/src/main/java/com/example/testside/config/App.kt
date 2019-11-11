package com.example.testside.config

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class App: DaggerApplication() {

   /* companion object {
        @JvmStatic
        lateinit var component: AppComponent
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent
        .builder()
        .create(this)
        .build()*/
    override fun applicationInjector(): AndroidInjector<out App> {
       return DaggerAppComponent.builder().create(this)
   }
}