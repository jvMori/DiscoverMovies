package com.example.jvmori.discovermovies.di.module

import android.content.Context
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.nowPlaying.NowPlayingRepository
import com.example.jvmori.discovermovies.data.repository.nowPlaying.NowPlayingRepositoryImpl
import com.example.jvmori.discovermovies.ui.presenter.nowPlaying.NowPlayingContract
import com.example.jvmori.discovermovies.ui.presenter.nowPlaying.NowPlayingPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NowPlayingModule{

    @Provides
    @Singleton
    fun provideNowPlayingRepository(tmdbAPI: TmdbAPI, context: Context) : NowPlayingRepository =
        NowPlayingRepositoryImpl(tmdbAPI, context)

    @Provides
    @Singleton
    fun provideNowPlayingPresenter(repository: NowPlayingRepository) : NowPlayingContract.NowPlayingPresenter =
        NowPlayingPresenterImpl(repository)
}