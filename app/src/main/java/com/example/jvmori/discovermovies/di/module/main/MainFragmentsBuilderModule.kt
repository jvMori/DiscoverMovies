package com.example.jvmori.discovermovies.di.module.main

import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import com.example.jvmori.discovermovies.ui.view.collections.CollectionFragment
import com.example.jvmori.discovermovies.ui.view.details.DetailsFragment
import com.example.jvmori.discovermovies.ui.view.discover.DiscoverFragment
import com.example.jvmori.discovermovies.ui.view.movies.GenreMoviesFragment
import com.example.jvmori.discovermovies.ui.view.movies.MoviesFragment
import com.example.jvmori.discovermovies.ui.view.movies.NowPlayingMoviesFragment
import com.example.jvmori.discovermovies.ui.view.movies.TrendingMoviesFragment
import com.example.jvmori.discovermovies.ui.view.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCollectionFragment() : CollectionFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment() : DetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeDiscoverFragment() : DiscoverFragment

    @ContributesAndroidInjector
    abstract fun contributeTrendingMoviesFragment() : TrendingMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeNowPlayingMoviesFragment() : NowPlayingMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeGenreMoviesFragment() : GenreMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment() : SearchFragment
}