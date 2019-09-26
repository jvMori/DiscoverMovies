package com.mori.jvmori.discovermovies.data.repository.collection

import android.content.Context
import android.util.Log
import com.mori.jvmori.discovermovies.data.local.entity.CollectionData
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.data.network.TmdbAPI
import com.mori.jvmori.discovermovies.data.repository.BaseSaveRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor (
    private var tmdbAPI: TmdbAPI,
    private var context: Context
) : CollectionRepository, BaseSaveRepository(tmdbAPI, context) {

    override fun insert(nameOfCollection: CollectionData) {
        Completable.fromAction {
            savedMovieDao.insertCollection(nameOfCollection)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.i("Error", "error")
            }.doOnComplete {
                Log.i("Completed", "completed")
            }.subscribe()
    }

    override fun delete(nameOfCollection: CollectionData) {
        Completable.fromAction {
              savedMovieDao.delete(nameOfCollection)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun getAllCollectionsNames(): Observable<List<CollectionData>> {
        return savedMovieDao.getAllCollections()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.i("Error", it.message)
            }
            .doOnComplete {
                Log.i("Complete", "Completed")
            }
    }

    override  fun displayAllSaved(collection: CollectionData): Observable<List<MovieResult>> {
        return savedMovieDao.getAllSaved(collection.collectionName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}