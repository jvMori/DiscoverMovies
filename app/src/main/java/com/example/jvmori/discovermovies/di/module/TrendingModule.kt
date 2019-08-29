package com.example.jvmori.discovermovies.di.module

import android.content.Context
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.trending.TrendingRepository
import com.example.jvmori.discovermovies.data.repository.trending.TrendingRepositoryImpl
import com.example.jvmori.discovermovies.ui.presenter.trending.TrendingContract
import com.example.jvmori.discovermovies.ui.presenter.trending.TrendingPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TrendingModule{

    @Provides
    @Singleton
    fun provideTrendingRepository(tmdbAPI: TmdbAPI, context: Context) : TrendingRepository =
        TrendingRepositoryImpl(tmdbAPI, context)

    @Provides
    @Singleton
    fun provideTrendingPresenter(repository: TrendingRepository) : TrendingContract.TrendingPresenter =
        TrendingPresenterImpl(repository)


}