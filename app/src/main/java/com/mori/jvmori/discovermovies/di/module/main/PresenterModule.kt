package com.mori.jvmori.discovermovies.di.module.main

import com.mori.jvmori.discovermovies.data.repository.collection.CollectionRepository
import com.mori.jvmori.discovermovies.data.repository.movies.MoviesRepository
import com.mori.jvmori.discovermovies.di.scope.MainActivityScope
import com.mori.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenter
import com.mori.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenterImpl
import com.mori.jvmori.discovermovies.ui.presenter.search.SearchPresenter
import com.mori.jvmori.discovermovies.ui.presenter.search.SearchPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    @MainActivityScope
    fun provideSearchPresenter(repository: MoviesRepository) : SearchPresenter =
        SearchPresenterImpl(repository)


    @Provides
    @MainActivityScope
    fun provideSavingPresenter(repository: MoviesRepository, repositoryCol : CollectionRepository) : SavingBasePresenter =
        SavingBasePresenterImpl(repository, repositoryCol)

}