package com.example.jvmori.discovermovies.di.module

import com.example.jvmori.discovermovies.data.repository.collection.CollectionRepository
import com.example.jvmori.discovermovies.data.repository.collection.CollectionRepositoryImpl
import com.example.jvmori.discovermovies.data.repository.movies.MoviesRepository
import com.example.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenter
import com.example.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenterImpl
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenter
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.example.jvmori.discovermovies.ui.presenter.search.SearchPresenter
import com.example.jvmori.discovermovies.ui.presenter.search.SearchPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideSearchPresenter(repository: MoviesRepository) : SearchPresenter =
        SearchPresenterImpl(repository)


    @Provides
    @Singleton
    fun provideSavingPresenter(repository: MoviesRepository, repositoryCol : CollectionRepository) : SavingBasePresenter =
        SavingBasePresenterImpl(repository, repositoryCol)

}