package com.example.jvmori.discovermovies.di.module

import android.content.Context
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.example.jvmori.discovermovies.data.repository.movies.MoviesRepository
import com.example.jvmori.discovermovies.data.repository.movies.MoviesRepositoryImpl
import com.example.jvmori.discovermovies.data.repository.trending.TrendingRepositoryImpl
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenter
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [
    DatabaseModule::class
])
class MoviesModule  {

    @Provides
    @Named("Movies")
    fun provideMoviesPresenter(@Named("Movies") repository: BaseMoviesRepository) : MoviesPresenterInterface =
        MoviesPresenter(repository)

    @Provides
    @Singleton
    @Named("Movies")
    fun provideBaseMoviesRepository(
        context: Context,
        moviesDao: MovieDao,
        tmdbAPI: TmdbAPI
    ): BaseMoviesRepository =
        MoviesRepositoryImpl(context, tmdbAPI, moviesDao)

    @Provides
    @Singleton
    fun provideMoviesRepository(
        context: Context,
        moviesDao: MovieDao,
        tmdbAPI: TmdbAPI
    ): MoviesRepository =
        MoviesRepositoryImpl(context, tmdbAPI, moviesDao)
}