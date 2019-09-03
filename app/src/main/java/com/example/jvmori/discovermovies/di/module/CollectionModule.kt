package com.example.jvmori.discovermovies.di.module

import android.content.Context
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.collection.CollectionRepository
import com.example.jvmori.discovermovies.data.repository.collection.CollectionRepositoryImpl
import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionPresenter
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CollectionModule {
    @Provides
    @MainActivityScope
    fun provideNowCollectionRepository(tmdbAPI: TmdbAPI, context: Context) : CollectionRepository =
        CollectionRepositoryImpl(tmdbAPI, context)

    @Provides
    @MainActivityScope
    fun provideCollectionPresenter(repository: CollectionRepository) : CollectionPresenter =
        CollectionPresenterImpl(repository)
}