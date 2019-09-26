package com.example.jvmori.discovermovies.di.module.main

import android.app.Application
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.example.jvmori.discovermovies.data.repository.nowPlaying.NowPlayingRepositoryImpl
import com.example.jvmori.discovermovies.data.repository.trending.TrendingRepositoryImpl
import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenter
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.example.jvmori.discovermovies.ui.presenter.nowPlaying.NowPlayingContract
import com.example.jvmori.discovermovies.ui.presenter.nowPlaying.NowPlayingPresenterImpl
import com.example.jvmori.discovermovies.ui.presenter.trending.TrendingContract
import com.example.jvmori.discovermovies.ui.presenter.trending.TrendingPresenterImpl
import com.example.jvmori.discovermovies.util.Const
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class NowPlayingModule{

    @Provides
    @MainActivityScope
    @Named(Const.nowPlayingNamed)
    fun provideNowPlayingRepository(tmdbAPI: TmdbAPI, movieDao: MovieDao): BaseMoviesRepository =
        NowPlayingRepositoryImpl(movieDao, tmdbAPI)

    @Provides
    @Named(Const.nowPlayingNamed)
    fun provideBaseNowPlayingPresenter(@Named(Const.nowPlayingNamed) repository: BaseMoviesRepository): MoviesPresenterInterface =
        MoviesPresenter(repository)

    @Provides
    @MainActivityScope
    fun provideNowPlayingPresenter(@Named(Const.nowPlayingNamed) repository: BaseMoviesRepository): NowPlayingContract.NowPlayingPresenter =
        NowPlayingPresenterImpl(repository)

}