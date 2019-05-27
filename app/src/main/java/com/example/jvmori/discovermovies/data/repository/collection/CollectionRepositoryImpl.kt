package com.example.jvmori.discovermovies.data.repository.collection

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.BaseRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor (
    private var tmdbAPI: TmdbAPI,
    private var context: Context
) : CollectionRepository, BaseRepository(tmdbAPI, context) {

    override  fun displayAllSaved(collection: String): Observable<List<MovieResult>> {
        return savedMovieDao.getAllSaved(collection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}