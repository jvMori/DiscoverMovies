package com.example.jvmori.discovermovies.data.repository.movies

import android.content.Context
import android.util.Log
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.BaseSaveRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private var context: Context,
    private var tmdbAPI: TmdbAPI,
    override var moviesDao: MovieDao
) : MoviesRepository, BaseSaveRepository(tmdbAPI, context) {

    override fun getAllMoviesRemote(queryParam: DiscoverQueryParam): Maybe<DiscoverMovieResponse> {
        return getMoviesToDiscover(queryParam)
            .firstElement()
            .flatMap {
                it.genreId = queryParam.genresId.toInt()
                it.timestamp = System.currentTimeMillis()
                return@flatMap Maybe.just(it)
            }
            .doOnSuccess {
                moviesDao.updateData(it)
            }.doOnError {
                Log.i("Error", "Error")
            }
    }

    override fun getSearchedItems(q: String): Single<List<MovieResult>> {
        return tmdbAPI.getSearchedItems(q)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap Single.just(it.results)
            }
    }

    override fun getMovieFromDbByIdAndCategory(movie: MovieResult, category: String): Single<MovieResult> {
        return savedMovieDao.getMovieByIdAndCollection(movie.id, category)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun deleteMovie(movie: MovieResult) {
        Completable.fromAction { savedMovieDao.delete(movie) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    private fun getMoviesToDiscover(queryParam: DiscoverQueryParam): Observable<DiscoverMovieResponse> {
        val parameters: HashMap<String, String> = HashMap()
        parameters["sort_by"] = "popularity.desc"
        parameters["page"] = queryParam.page.toString()
        parameters["vote_average.gte"] = queryParam.vote.toString()
        parameters["with_genres"] = queryParam.genresId
        parameters["year"] = queryParam.year

        return tmdbAPI.getMovies(parameters)
    }
}