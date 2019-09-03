package com.example.jvmori.discovermovies.di.module

import android.content.Context
import com.example.jvmori.discovermovies.data.local.GenreDao
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.genres.GenresRepository
import com.example.jvmori.discovermovies.data.repository.genres.GenresRepositoryImpl
import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import com.example.jvmori.discovermovies.ui.presenter.genres.GenresPresenter
import com.example.jvmori.discovermovies.ui.presenter.genres.GenresPresenterInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GenresModule{

    @Provides
    @MainActivityScope
    fun provideGenresPresenter(repository: GenresRepository) : GenresPresenterInterface =
        GenresPresenter(repository)

    @Provides
    @MainActivityScope
    fun provideGenreRepository(tmdbAPI: TmdbAPI, context: Context, genreDao : GenreDao) : GenresRepository =
        GenresRepositoryImpl(tmdbAPI, context, genreDao)
}