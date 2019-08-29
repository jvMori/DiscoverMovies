package com.example.jvmori.discovermovies.data.repository.trending

import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import io.reactivex.Observable
import io.reactivex.Single

interface TrendingRepository : BaseMoviesRepository {
    fun fetchTrendingMoviesRemote(period: String): Observable<DiscoverMovieResponse>
    fun fetchTrendingLocal(period: String) : Single<List<MovieResult>>
    fun connectTrending()
    fun setConnectableTrendings(period: String)
    fun isTrendingMovieUpToDate(movie: MovieResult): Boolean
}