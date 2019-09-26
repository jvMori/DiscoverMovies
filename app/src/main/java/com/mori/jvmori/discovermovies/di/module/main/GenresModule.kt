package com.mori.jvmori.discovermovies.di.module.main

import android.app.Application
import com.mori.jvmori.discovermovies.data.local.GenreDao
import com.mori.jvmori.discovermovies.data.network.TmdbAPI
import com.mori.jvmori.discovermovies.data.repository.genres.GenresRepository
import com.mori.jvmori.discovermovies.data.repository.genres.GenresRepositoryImpl
import com.mori.jvmori.discovermovies.di.scope.MainActivityScope
import com.mori.jvmori.discovermovies.ui.presenter.genres.GenresPresenter
import com.mori.jvmori.discovermovies.ui.presenter.genres.GenresPresenterInterface
import dagger.Module
import dagger.Provides

@Module
class GenresModule{

    @Provides
    @MainActivityScope
    fun provideGenresPresenter(repository: GenresRepository) : GenresPresenterInterface =
        GenresPresenter(repository)

    @Provides
    @MainActivityScope
    fun provideGenreRepository(tmdbAPI: TmdbAPI, context: Application, genreDao : GenreDao) : GenresRepository =
        GenresRepositoryImpl(tmdbAPI, context, genreDao)
}