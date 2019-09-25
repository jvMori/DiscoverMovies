package com.mori.jvmori.discovermovies.di.module.main

import com.mori.jvmori.discovermovies.data.network.TmdbAPI
import com.mori.jvmori.discovermovies.data.repository.details.DetailsRepository
import com.mori.jvmori.discovermovies.data.repository.details.DetailsRepositoryImpl
import com.mori.jvmori.discovermovies.di.scope.MainActivityScope
import com.mori.jvmori.discovermovies.ui.presenter.details.DetailsPresenter
import com.mori.jvmori.discovermovies.ui.presenter.details.DetailsPresenterImpl
import dagger.Module
import dagger.Provides

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