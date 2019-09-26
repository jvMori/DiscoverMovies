package com.mori.jvmori.discovermovies.di.module.main

import android.app.Application
import com.mori.jvmori.discovermovies.data.network.TmdbAPI
import com.mori.jvmori.discovermovies.data.repository.collection.CollectionRepository
import com.mori.jvmori.discovermovies.data.repository.collection.CollectionRepositoryImpl
import com.mori.jvmori.discovermovies.di.scope.MainActivityScope
import com.mori.jvmori.discovermovies.ui.presenter.collections.CollectionPresenter
import com.mori.jvmori.discovermovies.ui.presenter.collections.CollectionPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class CollectionModule {

    @Provides
    @MainActivityScope
    fun provideNowCollectionRepository(tmdbAPI: TmdbAPI, context: Application) : CollectionRepository =
        CollectionRepositoryImpl(tmdbAPI, context)

    @Provides
    @MainActivityScope
    fun provideCollectionPresenter(repository: CollectionRepository) : CollectionPresenter =
        CollectionPresenterImpl(repository)
}