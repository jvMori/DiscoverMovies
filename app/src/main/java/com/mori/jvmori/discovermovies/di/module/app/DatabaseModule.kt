package com.mori.jvmori.discovermovies.di.module.app

import android.app.Application
import com.mori.jvmori.discovermovies.data.local.GenreDao
import com.mori.jvmori.discovermovies.data.local.MovieDao
import com.mori.jvmori.discovermovies.data.local.database.MovieDatabase
import com.mori.jvmori.discovermovies.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @ApplicationScope
    fun provideMoviesDao(context: Application): MovieDao = MovieDatabase.invoke(context.applicationContext).moviesDao()

    @Provides
    @ApplicationScope
    fun provideGenreDao(context : Application) : GenreDao =  MovieDatabase.invoke(context.applicationContext).genreDao()
}