package com.example.jvmori.discovermovies.data.repository.trending

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Observable
import io.reactivex.Single

interface TrendingRepository {
    fun fetchTrendingMoviesRemote(period: String): Observable<List<MovieResult>>
    fun fetchTrendingLocal(period: String) : Single<List<MovieResult>>
    fun connectTrending()
    fun setConnectableTrendings(period: String)
    fun isTrendingMovieUpToDate(movie: MovieResult): Boolean
}