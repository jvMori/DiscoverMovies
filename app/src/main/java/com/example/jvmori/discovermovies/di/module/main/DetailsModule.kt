package com.example.jvmori.discovermovies.di.module.main

import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.details.DetailsRepository
import com.example.jvmori.discovermovies.data.repository.details.DetailsRepositoryImpl
import com.example.jvmori.discovermovies.di.scope.ApplicationScope
import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import com.example.jvmori.discovermovies.ui.presenter.details.DetailsPresenter
import com.example.jvmori.discovermovies.ui.presenter.details.DetailsPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DetailsModule {
    @Provides
    @MainActivityScope
    fun provideDetailsRepository (tmdbAPI: TmdbAPI): DetailsRepository = DetailsRepositoryImpl(tmdbAPI)

    @Provides
    @MainActivityScope
    fun provideDetailsPresenter(repository: DetailsRepository) : DetailsPresenter =
        DetailsPresenterImpl(repository)
}