package com.example.jvmori.discovermovies.data.repository.collection

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.BaseRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor (
    private var tmdbAPI: TmdbAPI,
    private var context: Context
) : CollectionRepository, BaseRepository(tmdbAPI, context) {

    override  fun displayAllSaved(collection: String): LiveData<List<MovieResult>> {
        return LiveDataReactiveStreams.fromPublisher {
            savedMovieDao.getAllSaved(collection)
                .subscribeOn(Schedulers.io())
        }
    }
}