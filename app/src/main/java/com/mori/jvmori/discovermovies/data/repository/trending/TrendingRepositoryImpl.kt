package com.mori.jvmori.discovermovies.data.repository.trending

import android.util.Log
import com.mori.jvmori.discovermovies.data.local.MovieDao
import com.mori.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.mori.jvmori.discovermovies.data.network.TmdbAPI
import com.mori.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.mori.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import com.mori.jvmori.discovermovies.util.Const
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor (
    private var tmdbApi: TmdbAPI, override var moviesDao: MovieDao
) : BaseMoviesRepository {

    override fun getAllMoviesRemote(queryParam: DiscoverQueryParam): Maybe<DiscoverMovieResponse> {
        return tmdbApi.getTrendingMovies(queryParam.period)
            .firstElement()
            .flatMap {
                it.genreId = Const.genreIdForTrendingMovies
                it.timestamp = System.currentTimeMillis()
                return@flatMap Maybe.just(it)
            }
            .doOnSuccess {
                moviesDao.updateData(it)
            }.doOnError {
                Log.i("Error", "Error")
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}