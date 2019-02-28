package com.example.jvmori.discovermovies.data.repository

import android.content.Context
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.response.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesRepository(
    private val tmdpApi: TmdbAPI,
    context: Context
) {
    private val genreDao = MovieDatabase.invoke(context.applicationContext).genreDao()

    private fun getMoviesToDiscover(
        queryParam: DiscoverQueryParam
    ): Observable<DiscoverMovieResponse> {
        val parameters: HashMap<String, String> = HashMap()
        parameters["sort_by"] = "popularity.desc"
        parameters["page"] = queryParam.page.toString()
        parameters["vote_average.gte"] = queryParam.vote.toString()
        parameters["with_genres"] = queryParam.genresId
        parameters["year"] = queryParam.year

        return tmdpApi.getMovies(parameters)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun moviesObservable(queryParam: DiscoverQueryParam) : Observable<List<MovieResult>>{
        return getMoviesToDiscover(queryParam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap Observable.just(it.results)
            }
    }

    fun getDetails(movieResult: MovieResult): Observable<MovieResult> {
        return tmdpApi.getMovieDetails(movieResult.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { movieDetails: MovieDetails ->
                movieResult.movieDetails = movieDetails
                return@map movieResult
            }

    }

    fun getGenres() : Observable<List<Genre>>{
        return Observable.concat(getAllGenresLocal(), getAllGenresRemote())
            .filter{
                list -> !list.isEmpty()
            }
            .take(1)
    }

    private fun getAllGenresRemote(): Observable<List<Genre>> {
        return tmdpApi.getGenres()
            .flatMap {
                return@flatMap Observable.just(it.genres)
            }
            .doOnNext{
                saveData(it)
            }
            .subscribeOn(Schedulers.io())
    }

    private fun saveData(data : List<Genre>)
    {
        genreDao.insert(data)
    }

    private fun getAllGenresLocal() : Observable<List<Genre>>{
        return genreDao.getAllGenres()
    }

    fun getGenreById(genreId: Int): Single<Genre> {
        return genreDao.getGenre(genreId)
    }

}