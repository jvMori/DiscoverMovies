package com.example.jvmori.discovermovies.data.repository.trending

import android.content.Context
import android.util.Log
import com.example.jvmori.discovermovies.data.local.entity.Category
import com.example.jvmori.discovermovies.data.local.Collection
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.BaseRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.Maybe
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

    override fun getMovies(queryParam: DiscoverQueryParam): Observable<DiscoverMovieResponse> {
        return fetchTrendingMoviesRemote("week")
    }

    override fun fetchTrendingMoviesRemote(period: String): Observable<DiscoverMovieResponse> {
        return  tmdbApi.getTrendingMovies(period)
            .toObservable()
            .doOnNext {
                saveMovies(period, Category.TRENDING.toString(), it.results, Collection.NONE.toString())
            }
            .doOnError {
                Log.i("Error", it.message)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchTrendingLocal(period: String): Single<List<MovieResult>> {
        return savedMovieDao.getAllFromCategory(period, Category.TRENDING.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun isTrendingMovieUpToDate(movie: MovieResult): Boolean {
        return isMovieUpToDate(movie)
    }
}