package com.mori.jvmori.discovermovies.di.component

import android.app.Application
import com.mori.jvmori.discovermovies.application.MoviesApplication
import com.mori.jvmori.discovermovies.di.module.app.ActivityBuildersModule
import com.mori.jvmori.discovermovies.di.module.app.DatabaseModule
import com.mori.jvmori.discovermovies.di.module.app.NetworkModule
import com.mori.jvmori.discovermovies.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
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