package com.example.testside.config

import android.app.Application
import com.example.testside.config.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivitiesBindingModule::class,
        FragmentBindingModule::class,
        ApiModule::class,
        PresenterModule::class,
        ServiceModule::class,
        ManagerModule::class
    ]
)

/*interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Buil*/
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}


