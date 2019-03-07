package com.example.jvmori.discovermovies.di.module

import com.example.jvmori.discovermovies.ui.view.details.DetailsPresenter
import com.example.jvmori.discovermovies.ui.view.details.DetailsPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DetailsPresenterModule {
    @Provides
    @Singleton
    fun provideDetailsPresenter() : DetailsPresenter = DetailsPresenterImpl()
}