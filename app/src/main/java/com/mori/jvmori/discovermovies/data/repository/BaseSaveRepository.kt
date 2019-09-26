package com.mori.jvmori.discovermovies.data.repository

import android.content.Context
import com.mori.jvmori.discovermovies.data.local.database.MovieDatabase
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.data.network.TmdbAPI
import com.mori.jvmori.discovermovies.util.Const
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseSaveRepository(
    open var tmdbApi: TmdbAPI,
    context: Context
){
    val savedMovieDao = MovieDatabase.invoke(context.applicationContext).savedMovieDao()

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
}