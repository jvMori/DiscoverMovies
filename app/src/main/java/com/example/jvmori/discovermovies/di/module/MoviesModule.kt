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
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class MoviesModule {

    @Provides
    @Singleton
   // @Named("MoviesForSpecificGenre")
    fun provideMoviesPresenter(@Named("MoviesForSpecificGenre") repository: BaseMoviesRepository) : MoviesPresenterInterface =
        MoviesPresenter(repository)

    @Provides
    @Singleton
    @Named("TrendingMovies")
    fun provideTrendingPresenter(@Named("TrendingMovies") repository: BaseMoviesRepository) : MoviesPresenterInterface =
        MoviesPresenter(repository)

    @Provides
    @Singleton
    fun provideMoviesDao(context: Context): MovieDao = MovieDatabase.invoke(context).moviesDao()

    @Provides
    @Singleton
    @Named("MoviesForSpecificGenre")
    fun provideBaseMoviesRepository(
        context: Context,
        moviesDao: MovieDao,
        tmdbAPI: TmdbAPI
    ): BaseMoviesRepository =
        MoviesRepositoryImpl(context, moviesDao, tmdbAPI)

    @Provides
    @Singleton
    fun provideMoviesRepository(
        context: Context,
        moviesDao: MovieDao,
        tmdbAPI: TmdbAPI
    ): MoviesRepository =
        MoviesRepositoryImpl(context, moviesDao, tmdbAPI)
}