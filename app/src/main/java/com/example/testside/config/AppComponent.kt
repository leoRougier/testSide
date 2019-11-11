package com.example.testside.config

import android.app.Application
import com.example.testside.config.module.ActivitiesBindingModule
import com.example.testside.config.module.ApiModule
import com.example.testside.config.module.PresenterModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivitiesBindingModule::class,
        ApiModule.ApiModule::class,
        PresenterModule::class
    ]
)

interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun build(): AppComponent
    }
}