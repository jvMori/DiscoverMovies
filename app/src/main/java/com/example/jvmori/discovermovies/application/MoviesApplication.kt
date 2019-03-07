package com.example.jvmori.discovermovies.application

import android.app.Application
import com.example.jvmori.discovermovies.di.component.AppComponent
import com.example.jvmori.discovermovies.di.component.DaggerAppComponent
import com.example.jvmori.discovermovies.di.module.AppModule

class MoviesApplication : Application() {
    lateinit var movieComponent : AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        movieComponent = initDagger(this)
    }

    private fun initDagger(app: MoviesApplication) : AppComponent{
        return DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
    }
}