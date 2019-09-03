package com.example.jvmori.discovermovies.di.module.main

import android.app.Application
import android.content.Context
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.collection.CollectionRepository
import com.example.jvmori.discovermovies.data.repository.collection.CollectionRepositoryImpl
import com.example.jvmori.discovermovies.di.scope.ApplicationScope
import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionPresenter
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CollectionModule {

    @Provides
    @Singleton
    fun provideNowCollectionRepository(tmdbAPI: TmdbAPI, context: Application) : CollectionRepository =
        CollectionRepositoryImpl(tmdbAPI, context)

    @Provides
    @Singleton
    fun provideCollectionPresenter(repository: CollectionRepository) : CollectionPresenter =
        CollectionPresenterImpl(repository)
}