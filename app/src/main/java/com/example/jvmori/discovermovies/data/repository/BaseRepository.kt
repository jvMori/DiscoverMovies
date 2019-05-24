package com.example.jvmori.discovermovies.data.repository

import android.content.Context
import android.util.Log
import com.example.jvmori.discovermovies.MainActivity
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

abstract class BaseRepository(
    open var tmdbApi: TmdbAPI,
    context: Context
){
    val genreDao = MovieDatabase.invoke(context.applicationContext).genreDao()
    val moviesDao = MovieDatabase.invoke(context.applicationContext).moviesDao()
    val savedMovieDao = MovieDatabase.invoke(context.applicationContext).savedMovieDao()

    fun saveMovies(period: String, category : String, data: List<MovieResult>) {
        data.forEach {
            it.category = category
            it.period = period
            it.timestamp = System.currentTimeMillis()
            it.mediaType = Const.MOVIE
        }
        Completable.fromAction{
            savedMovieDao.updateMovies(period, category, data)
        }.subscribeOn(Schedulers.io())
            .doOnError {
                Log.i(MainActivity.TAG, "error while saving trending movies")
            }
            .subscribe()
    }
    fun isMovieUpToDate(movie: MovieResult): Boolean {
        return movie.timestamp != 0L && System.currentTimeMillis() - movie.timestamp < Const.STALE_MS
    }
}