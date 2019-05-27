package com.example.jvmori.discovermovies.data.repository.trending

import android.content.Context
import com.example.jvmori.discovermovies.data.local.entity.Category
import com.example.jvmori.discovermovies.data.local.entity.Collection
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.BaseRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor (
    override var tmdbApi: TmdbAPI,
    context: Context
) : TrendingRepository, BaseRepository(tmdbApi, context){

    private lateinit var connectableObservableTrending : ConnectableObservable<DiscoverMovieResponse>

    override fun fetchTrendingMoviesRemote(period: String): Observable<List<MovieResult>> {
        return connectableObservableTrending
            .flatMap {
                return@flatMap Observable.just(it.results)
            }
            .doOnNext {
                saveMovies(period, Category.TRENDING.toString(), it, Collection.NONE.toString())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchTrendingLocal(period: String): Single<List<MovieResult>> {
        return savedMovieDao.getAllTrending(period, Category.TRENDING.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun connectTrending() {
        connectableObservableTrending.connect()
    }

    override fun setConnectableTrendings(period: String) {
        connectableObservableTrending =
                tmdbApi.getTrendingMovies(period)
                    .toObservable()
                    .subscribeOn(Schedulers.io())
                    .replay()
    }

    override fun isTrendingMovieUpToDate(movie: MovieResult): Boolean {
        return isMovieUpToDate(movie)
    }

    fun getTrending(period: String) : Observable<List<MovieResult>>{
        return Observable.concat(
            fetchTrendingLocal(period).toObservable(),
            fetchTrendingMoviesRemote(period))
            .takeWhile { data -> data.isNotEmpty() && isMovieUpToDate(data[0]) }
            .take(1)
    }

}