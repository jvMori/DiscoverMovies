package com.example.jvmori.discovermovies.data.repository.nowPlaying

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Flowable

interface NowPlayingRepository {
    fun getNowPlaying() : Flowable<List<MovieResult>>
}