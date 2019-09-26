package com.example.jvmori.discovermovies.data.repository.nowPlaying

import android.util.Log
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingRepositoryImpl @Inject constructor (
    override var moviesDao: MovieDao,
    private var tmdbApi: TmdbAPI
) : BaseMoviesRepository {

    override fun getAllMoviesRemote(queryParam: DiscoverQueryParam): Maybe<DiscoverMovieResponse> {
        return tmdbApi.getNowPlaying()
            .firstElement()
            .flatMap {
                it.genreId = Const.genreIdForNowPlayingMovies
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