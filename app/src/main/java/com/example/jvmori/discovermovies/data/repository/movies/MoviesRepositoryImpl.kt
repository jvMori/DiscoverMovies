package com.example.jvmori.discovermovies.data.repository.movies

import android.content.Context
import android.util.Log
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.local.entity.Collection
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.repository.BaseRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private var context: Context,
    private var moviesDao : MovieDao,
    private var tmdbAPI: TmdbAPI
) : MoviesRepository, BaseRepository(tmdbAPI, context) {

    override fun getMovies(queryParam: DiscoverQueryParam): Observable<DiscoverMovieResponse> {
        return Maybe.concat(getAllMoviesLocal(queryParam), getAllMoviesRemote(queryParam))
            .filter { movieResponse ->
                movieResponse.results.isNotEmpty() && isMovieUpToDate(movieResponse)
            }
            .take(1)
            .toObservable()
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
        return savedMovieDao.getMovieByIdAndCategory(movie.id, category)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun deleteMovie(movie: MovieResult) {
        Completable.fromAction { savedMovieDao.delete(movie) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }


    private fun getAllMoviesRemote(queryParam: DiscoverQueryParam): Maybe<DiscoverMovieResponse> {
        return getMoviesToDiscover(queryParam)
            .firstElement()
            .flatMap {
                it.genreId = queryParam.genresId.toInt()
                it.timestamp = System.currentTimeMillis()
                return@flatMap Maybe.just(it)
            }
            .doAfterSuccess {
                moviesDao.updateData(it)
            }.doOnError {
                Log.i("Error", "Error")
            }
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

    private fun getAllMoviesLocal(queryParam: DiscoverQueryParam): Maybe<DiscoverMovieResponse> {
        return moviesDao.getMovies(queryParam.genresId.toInt(), queryParam.page).doAfterSuccess {
            Log.i("Movies", it.toString())
        }
    }

    private fun isMovieUpToDate(movie: DiscoverMovieResponse): Boolean {
        return movie.timestamp != 0L && System.currentTimeMillis() - movie.timestamp < Const.STALE_MS
    }
}