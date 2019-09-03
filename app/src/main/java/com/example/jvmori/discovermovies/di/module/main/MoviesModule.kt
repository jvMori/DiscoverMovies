package com.example.jvmori.discovermovies.di.module.main

import android.app.Application
import android.content.Context
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.example.jvmori.discovermovies.data.repository.movies.MoviesRepository
import com.example.jvmori.discovermovies.data.repository.movies.MoviesRepositoryImpl
import com.example.jvmori.discovermovies.di.module.app.DatabaseModule
import com.example.jvmori.discovermovies.di.scope.ApplicationScope
import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenter
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class MoviesModule  {

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