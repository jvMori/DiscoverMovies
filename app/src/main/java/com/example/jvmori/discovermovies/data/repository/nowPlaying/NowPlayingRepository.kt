package com.example.jvmori.discovermovies.data.repository.nowPlaying

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Flowable
import io.reactivex.Single

interface NowPlayingRepository {
    fun getNowPlayingRemote() : Flowable<List<MovieResult>>
    fun getNowPlayingLocal() : Single<List<MovieResult>>
    fun isMovieUpToDate(movie: MovieResult): Boolean
}