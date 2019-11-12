package com.example.testside.config

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



/*class App: DaggerApplication() {

    /*override fun applicationInjector(): AndroidInjector<out App> {
       return DaggerAppComponent.builder().create(this)
   }*/
}*/
class App : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}