package com.mori.jvmori.discovermovies.di.module.main

import com.mori.jvmori.discovermovies.data.local.MovieDao
import com.mori.jvmori.discovermovies.data.network.TmdbAPI
import com.mori.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.mori.jvmori.discovermovies.data.repository.nowPlaying.NowPlayingRepositoryImpl
import com.mori.jvmori.discovermovies.di.scope.MainActivityScope
import com.mori.jvmori.discovermovies.ui.presenter.movies.MoviesPresenter
import com.mori.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.mori.jvmori.discovermovies.ui.presenter.nowPlaying.NowPlayingContract
import com.mori.jvmori.discovermovies.ui.presenter.nowPlaying.NowPlayingPresenterImpl
import com.mori.jvmori.discovermovies.util.Const
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