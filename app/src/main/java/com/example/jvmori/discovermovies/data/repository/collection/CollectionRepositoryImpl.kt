package com.example.jvmori.discovermovies.data.repository.collection

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.jvmori.discovermovies.data.local.entity.CollectionType
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.BaseRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor (
    private var tmdbAPI: TmdbAPI,
    private var context: Context
) : CollectionRepository, BaseRepository(tmdbAPI, context) {

    override fun insert(nameOfCollection: CollectionType) {
        Completable.fromAction {
            savedMovieDao.insertCollection(nameOfCollection)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun delete(nameOfCollection: CollectionType) {
        Completable.fromAction {
              savedMovieDao.delete(nameOfCollection)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllCollectionsNames(): Observable<CollectionType> {
        return savedMovieDao.getAllCollections()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override  fun displayAllSaved(collection: CollectionType): Observable<List<MovieResult>> {
        return savedMovieDao.getAllSaved(collection.colName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}