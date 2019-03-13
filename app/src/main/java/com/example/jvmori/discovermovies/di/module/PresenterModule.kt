package com.example.jvmori.discovermovies.di.module

import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.view.details.DetailsPresenter
import com.example.jvmori.discovermovies.ui.view.details.DetailsPresenterImpl
import com.example.jvmori.discovermovies.ui.view.discover.GenresPresenter
import com.example.jvmori.discovermovies.ui.view.discover.GenresPresenterInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun provideDetailsPresenter(repository: MoviesRepository) : DetailsPresenter = DetailsPresenterImpl(repository)

    @Provides
    @Singleton
    fun provideGenresPresenter(repository: MoviesRepository) :GenresPresenterInterface = GenresPresenter(repository)
}