package com.example.jvmori.discovermovies.di.module.main

import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import com.example.jvmori.discovermovies.ui.view.collections.CollectionFragment
import com.example.jvmori.discovermovies.ui.view.details.DetailsFragment
import com.example.jvmori.discovermovies.ui.view.discover.DiscoverFragment
import com.example.jvmori.discovermovies.ui.view.movies.MoviesFragment
import com.example.jvmori.discovermovies.ui.view.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun contributeCollectionFragment() : CollectionFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment() : DetailsFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun contributeDiscoverFragment() : DiscoverFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMoviesFragment() : MoviesFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment() : SearchFragment
}