package com.mori.jvmori.discovermovies.di.module.main

import com.mori.jvmori.discovermovies.ui.view.collections.CollectionDetailsFragment
import com.mori.jvmori.discovermovies.ui.view.collections.CollectionFragment
import com.mori.jvmori.discovermovies.ui.view.details.DetailsFragment
import com.mori.jvmori.discovermovies.ui.view.discover.DiscoverFragment
import com.mori.jvmori.discovermovies.ui.view.movies.GenreMoviesFragment
import com.mori.jvmori.discovermovies.ui.view.movies.NowPlayingMoviesFragment
import com.mori.jvmori.discovermovies.ui.view.movies.TrendingMoviesFragment
import com.mori.jvmori.discovermovies.ui.view.search.SearchFragment
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
    abstract fun contributeCollectionDetailsFragment() : CollectionDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeGenreMoviesFragment() : GenreMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment() : SearchFragment
}