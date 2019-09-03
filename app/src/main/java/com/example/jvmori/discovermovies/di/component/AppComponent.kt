package com.example.jvmori.discovermovies.di.component

import android.app.Application
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.di.module.*
import com.example.jvmori.discovermovies.di.module.app.ActivityBuildersModule
import com.example.jvmori.discovermovies.di.module.app.DatabaseModule
import com.example.jvmori.discovermovies.di.module.app.NetworkModule
import com.example.jvmori.discovermovies.di.module.main.*
import com.example.jvmori.discovermovies.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityBuildersModule::class,
        DatabaseModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : AndroidInjector<MoviesApplication>{
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(application: Application): Builder
    }
}