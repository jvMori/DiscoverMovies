package com.example.jvmori.discovermovies.di.component

import com.example.jvmori.discovermovies.di.module.AppModule
import com.example.jvmori.discovermovies.di.module.DetailsPresenterModule
import com.example.jvmori.discovermovies.di.module.NetworkModule
import com.example.jvmori.discovermovies.ui.view.details.DetailsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        DetailsPresenterModule::class,
        NetworkModule::class]
)
interface AppComponent {
    fun inject(target: DetailsFragment)
}