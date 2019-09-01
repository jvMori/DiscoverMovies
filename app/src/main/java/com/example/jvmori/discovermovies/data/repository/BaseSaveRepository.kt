package com.example.jvmori.discovermovies.data.repository

import android.content.Context
import android.util.Log
import com.example.jvmori.discovermovies.MainActivity
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.data.local.Collection
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

abstract class BaseSaveRepository(
    open var tmdbApi: TmdbAPI,
    context: Context
){
    val savedMovieDao = MovieDatabase.invoke(context.applicationContext).savedMovieDao()

    fun saveMovies(period: String, category : String, data: List<MovieResult>, collection: String) {
        data.forEach {
            updateInfo(it, category, period, collection)
        }
        Completable.fromAction{
            savedMovieDao.updateMovies(period, category, data)
        }.subscribeOn(Schedulers.io())
            .doOnError {
                Log.i(MainActivity.TAG, "error while saving trending movies")
            }
            .subscribe()
    }

    private fun updateInfo(
        it: MovieResult,
        category: String,
        period: String,
        collection: String
    ) {
        it.backdropPath = it.backdropPath ?: ""
        it.posterPath = it.posterPath ?: ""
        it.category = category
        it.period = period
        it.timestamp = System.currentTimeMillis()
        it.mediaType = Const.MOVIE
        it.collection = collection
    }

    fun saveMovie(movie: MovieResult, collection : String, category: String, period: String) {
        updateInfo(movie, category, period, collection)
        Completable.fromAction { savedMovieDao.insert(movie) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
    fun isMovieUpToDate(movie: MovieResult): Boolean {
        return movie.timestamp != 0L && System.currentTimeMillis() - movie.timestamp < Const.STALE_MS
    }
}