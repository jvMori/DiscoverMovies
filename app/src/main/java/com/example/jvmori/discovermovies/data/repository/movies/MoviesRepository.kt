package com.example.jvmori.discovermovies.data.repository.movies

import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.Observable
import io.reactivex.Single

interface MoviesRepository {
    fun getMovies(queryParam: DiscoverQueryParam): Observable<DiscoverMovieResponse>
    fun getSearchedItems(q: String): Single<List<MovieResult>>
    fun getMovieFromDbById(movie: MovieResult): Single<MovieResult>
    fun deleteMovie(movie: MovieResult)
    fun saveMovie(movie: MovieResult, collection : String, category: String, period: String)
}