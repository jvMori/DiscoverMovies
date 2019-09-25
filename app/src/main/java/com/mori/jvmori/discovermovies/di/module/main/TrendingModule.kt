package com.mori.jvmori.discovermovies.di.module.main

import com.mori.jvmori.discovermovies.data.local.MovieDao
import com.mori.jvmori.discovermovies.data.network.TmdbAPI
import com.mori.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.mori.jvmori.discovermovies.data.repository.trending.TrendingRepositoryImpl
import com.mori.jvmori.discovermovies.di.scope.MainActivityScope
import com.mori.jvmori.discovermovies.ui.presenter.movies.MoviesPresenter
import com.mori.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.mori.jvmori.discovermovies.ui.presenter.trending.TrendingContract
import com.mori.jvmori.discovermovies.ui.presenter.trending.TrendingPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class TrendingModule {

    @Provides
    @MainActivityScope
    @Named("TrendingMovies")
    fun provideTrendingRepository(tmdbAPI: TmdbAPI, movieDao: MovieDao): BaseMoviesRepository =
        TrendingRepositoryImpl(tmdbAPI, movieDao)

    @Provides
    @Named("TrendingMovies")
    fun provideBaseTrendingPresenter(@Named("TrendingMovies") repository: BaseMoviesRepository): MoviesPresenterInterface =
        MoviesPresenter(repository)

    @Provides
    @MainActivityScope
    fun provideTrendingPresenter(@Named("TrendingMovies") repository: BaseMoviesRepository): TrendingContract.TrendingPresenter =
        TrendingPresenterImpl(repository)

}