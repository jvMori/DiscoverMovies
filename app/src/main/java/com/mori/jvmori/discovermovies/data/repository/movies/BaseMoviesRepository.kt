package com.mori.jvmori.discovermovies.data.repository.movies

import android.util.Log
import com.mori.jvmori.discovermovies.data.local.MovieDao
import com.mori.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.mori.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import com.mori.jvmori.discovermovies.util.Const
import io.reactivex.Maybe
import io.reactivex.Observable

interface BaseMoviesRepository {
    var moviesDao: MovieDao
    fun getMovies(queryParam: DiscoverQueryParam): Observable<DiscoverMovieResponse> {
        return Maybe.concat(getAllMoviesLocal(queryParam), getAllMoviesRemote(queryParam))
            .filter { movieResponse ->
                movieResponse.results.isNotEmpty() && isMovieUpToDate(movieResponse)
            }
            .take(1)
            .toObservable()
    }

    fun getAllMoviesLocal(queryParam: DiscoverQueryParam): Maybe<DiscoverMovieResponse> {
        return moviesDao.getMovies(queryParam.genresId.toInt(), queryParam.page)
            .doOnSuccess {
                Log.i("Movies", it.toString())
            }
            .doOnComplete {
                Log.i("Movies", "No data in db!")
            }
    }

    fun getAllMoviesRemote(queryParam: DiscoverQueryParam): Maybe<DiscoverMovieResponse>
    fun isMovieUpToDate(movieResponse: DiscoverMovieResponse): Boolean {
        return movieResponse.timestamp != 0L && System.currentTimeMillis() - movieResponse.timestamp < Const.STALE_MS
    }
}