package com.example.jvmori.discovermovies.di.module.app

import android.content.Context
import com.example.jvmori.discovermovies.data.local.GenreDao
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @ApplicationScope
    fun provideMoviesDao(context: Context): MovieDao = MovieDatabase.invoke(context).moviesDao()

    @Provides
    @ApplicationScope
    fun provideGenreDao(context : Context) : GenreDao =  MovieDatabase.invoke(context.applicationContext).genreDao()
}