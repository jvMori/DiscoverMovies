package com.example.jvmori.discovermovies.data.repository.movies

import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.Observable

interface BaseMoviesRepository {
    fun getMovies(queryParam: DiscoverQueryParam): Observable<DiscoverMovieResponse>
}