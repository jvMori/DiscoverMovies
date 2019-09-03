package com.example.jvmori.discovermovies.di.module.app

import android.app.Application
import com.example.jvmori.discovermovies.data.local.GenreDao
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDao(context: Application): MovieDao = MovieDatabase.invoke(context.applicationContext).moviesDao()

    @Provides
    @Singleton
    fun provideGenreDao(context : Application) : GenreDao =  MovieDatabase.invoke(context.applicationContext).genreDao()
}