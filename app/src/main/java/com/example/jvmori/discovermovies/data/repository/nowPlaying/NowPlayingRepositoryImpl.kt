package com.example.jvmori.discovermovies.data.repository.nowPlaying

import android.content.Context
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.BaseRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingRepositoryImpl @Inject constructor (
    private var tmdbAPI: TmdbAPI,
    private val context: Context
) : NowPlayingRepository, BaseRepository(tmdbAPI, context) {

    override fun getNowPlaying(): Flowable<List<MovieResult>> {
        return tmdbApi.getNowPlaying()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap Flowable.just(it.results)
            }
    }
}