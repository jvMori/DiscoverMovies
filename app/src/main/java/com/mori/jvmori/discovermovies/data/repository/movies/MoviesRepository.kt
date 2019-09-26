package com.mori.jvmori.discovermovies.data.repository.movies

import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Single

interface MoviesRepository : BaseMoviesRepository {
    fun getSearchedItems(q: String): Single<List<MovieResult>>
    fun getMovieFromDbByIdAndCategory(movie: MovieResult, category: String): Single<MovieResult>
    fun deleteMovie(movie: MovieResult)
    fun saveMovie(movie: MovieResult, collection : String, category: String, period: String)
}