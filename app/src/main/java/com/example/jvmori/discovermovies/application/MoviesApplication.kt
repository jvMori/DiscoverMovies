package com.example.jvmori.discovermovies.application

import com.example.jvmori.discovermovies.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject



class MoviesApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}