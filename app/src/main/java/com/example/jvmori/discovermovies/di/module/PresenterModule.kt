package com.example.jvmori.discovermovies.di.module

import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.presenter.details.DetailsPresenter
import com.example.jvmori.discovermovies.ui.presenter.details.DetailsPresenterImpl
import com.example.jvmori.discovermovies.ui.presenter.genres.GenresPresenter
import com.example.jvmori.discovermovies.ui.presenter.genres.GenresPresenterInterface
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenter
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.example.jvmori.discovermovies.ui.presenter.search.SearchPresenter
import com.example.jvmori.discovermovies.ui.presenter.search.SearchPresenterImpl
import com.example.jvmori.discovermovies.ui.presenter.trending.TrendingContract
import com.example.jvmori.discovermovies.ui.presenter.trending.TrendingPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun provideDetailsPresenter(repository: MoviesRepository) : DetailsPresenter =
        DetailsPresenterImpl(repository)

    @Provides
    @Singleton
    fun provideGenresPresenter(repository: MoviesRepository) : GenresPresenterInterface =
        GenresPresenter(repository)

    @Provides
    fun provideMoviesPresenter(repository: MoviesRepository) : MoviesPresenterInterface =
        MoviesPresenter(repository)

    @Provides
    @Singleton
    fun provideSearchPresenter(repository: MoviesRepository) : SearchPresenter =
        SearchPresenterImpl(repository)

    @Provides
    @Singleton
    fun provideTrendingPresenter(repository: MoviesRepository) : TrendingContract.TrendingPresenter =
            TrendingPresenterImpl(repository)
}