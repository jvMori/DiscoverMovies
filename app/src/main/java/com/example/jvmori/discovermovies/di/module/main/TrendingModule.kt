package com.example.jvmori.discovermovies.di.module.main

import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.example.jvmori.discovermovies.data.repository.trending.TrendingRepositoryImpl
import com.example.jvmori.discovermovies.di.module.app.DatabaseModule
import com.example.jvmori.discovermovies.di.scope.ApplicationScope
import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenter
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.example.jvmori.discovermovies.ui.presenter.trending.TrendingContract
import com.example.jvmori.discovermovies.ui.presenter.trending.TrendingPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class TrendingModule {

    @Provides
    @Singleton
    @Named("TrendingMovies")
    fun provideTrendingRepository(tmdbAPI: TmdbAPI, movieDao: MovieDao): BaseMoviesRepository =
        TrendingRepositoryImpl(tmdbAPI, movieDao)

    @Provides
    @Singleton
    @Named("TrendingMovies")
    fun provideBaseTrendingPresenter(@Named("TrendingMovies") repository: BaseMoviesRepository): MoviesPresenterInterface =
        MoviesPresenter(repository)


    @Provides
    @Singleton
    fun provideTrendingPresenter(@Named("TrendingMovies") repository: BaseMoviesRepository): TrendingContract.TrendingPresenter =
        TrendingPresenterImpl(repository)

}