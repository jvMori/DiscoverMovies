package com.mori.jvmori.discovermovies.di.module.main

import android.app.Application
import com.mori.jvmori.discovermovies.data.local.MovieDao
import com.mori.jvmori.discovermovies.data.network.TmdbAPI
import com.mori.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.mori.jvmori.discovermovies.data.repository.movies.MoviesRepository
import com.mori.jvmori.discovermovies.data.repository.movies.MoviesRepositoryImpl
import com.mori.jvmori.discovermovies.di.scope.MainActivityScope
import com.mori.jvmori.discovermovies.ui.presenter.movies.MoviesPresenter
import com.mori.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MoviesModule {

    @Provides
    @Named("Movies")
    fun provideMoviesPresenter(@Named("Movies") repository: BaseMoviesRepository) : MoviesPresenterInterface =
        MoviesPresenter(repository)

    @Provides
    @MainActivityScope
    @Named("Movies")
    fun provideBaseMoviesRepository(
        context: Application,
        moviesDao: MovieDao,
        tmdbAPI: TmdbAPI
    ): BaseMoviesRepository =
        MoviesRepositoryImpl(context, tmdbAPI, moviesDao)

    @Provides
    @MainActivityScope
    fun provideMoviesRepository(
        context: Application,
        moviesDao: MovieDao,
        tmdbAPI: TmdbAPI
    ): MoviesRepository =
        MoviesRepositoryImpl(context, tmdbAPI, moviesDao)
}