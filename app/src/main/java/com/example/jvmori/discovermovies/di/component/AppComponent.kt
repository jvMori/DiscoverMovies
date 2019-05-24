package com.example.jvmori.discovermovies.di.component

import com.example.jvmori.discovermovies.di.module.AppModule
import com.example.jvmori.discovermovies.di.module.PresenterModule
import com.example.jvmori.discovermovies.di.module.NetworkModule
import com.example.jvmori.discovermovies.di.module.TrendingModule
import com.example.jvmori.discovermovies.ui.view.details.DetailsFragment
import com.example.jvmori.discovermovies.ui.view.discover.DiscoverFragment
import com.example.jvmori.discovermovies.ui.view.movies.MoviesFragment
import com.example.jvmori.discovermovies.ui.view.movies.MoviesFragmentArgs
import com.example.jvmori.discovermovies.ui.view.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        PresenterModule::class,
        NetworkModule::class,
        TrendingModule::class
    ]
)
interface AppComponent {
    fun inject(target: DetailsFragment)
    fun inject(target: DiscoverFragment)
    fun inject(target: MoviesFragment)
    fun inject(target: SearchFragment)
}