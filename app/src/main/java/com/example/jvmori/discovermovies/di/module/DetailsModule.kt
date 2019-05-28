package com.example.jvmori.discovermovies.di.module

import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.details.DetailsRepository
import com.example.jvmori.discovermovies.data.repository.details.DetailsRepositoryImpl
import com.example.jvmori.discovermovies.ui.presenter.details.DetailsPresenter
import com.example.jvmori.discovermovies.ui.presenter.details.DetailsPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DetailsModule {
    @Provides
    @Singleton
    fun provideDetailsRepository (tmdbAPI: TmdbAPI): DetailsRepository = DetailsRepositoryImpl(tmdbAPI)

    @Provides
    @Singleton
    fun provideDetailsPresenter(repository: DetailsRepository) : DetailsPresenter =
        DetailsPresenterImpl(repository)
}