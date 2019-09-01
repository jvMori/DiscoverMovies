package com.example.jvmori.discovermovies.data.repository.nowPlaying

import android.content.Context
import com.example.jvmori.discovermovies.data.local.entity.Category
import com.example.jvmori.discovermovies.data.local.Collection
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.BaseSaveRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingRepositoryImpl @Inject constructor (
    private var tmdbAPI: TmdbAPI,
    private val context: Context
) : NowPlayingRepository, BaseSaveRepository(tmdbAPI, context) {

    override fun getNowPlayingRemote(): Flowable<List<MovieResult>> {
        return tmdbApi.getNowPlaying()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
               saveMovies("none", Category.NOW_PLAYING.toString(), it.results, Collection.NONE.toString())
            }
            .flatMap {
                return@flatMap Flowable.just(it.results)
            }
    }

    override fun getNowPlayingLocal(): Single<List<MovieResult>> {
        return savedMovieDao.getAllFromCategory("none", Category.NOW_PLAYING.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}