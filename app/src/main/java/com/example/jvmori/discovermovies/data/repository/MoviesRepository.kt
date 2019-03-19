package com.example.jvmori.discovermovies.data.repository

import android.content.Context
import android.util.Log
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.response.movie.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.movie.MovieResult
import com.example.jvmori.discovermovies.data.network.response.video.VideoResponse
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesRepository @Inject constructor (
    private val tmdbApi: TmdbAPI,
    context: Context
) {
    private val genreDao = MovieDatabase.invoke(context.applicationContext).genreDao()
    private val moviesDao = MovieDatabase.invoke(context.applicationContext).moviesDao()

    private fun getMoviesToDiscover(
        queryParam: DiscoverQueryParam
    ): Observable<DiscoverMovieResponse> {
        val parameters: HashMap<String, String> = HashMap()
        parameters["sort_by"] = "popularity.desc"
        parameters["page"] = queryParam.page.toString()
        parameters["vote_average.gte"] = queryParam.vote.toString()
        parameters["with_genres"] = queryParam.genresId
        parameters["year"] = queryParam.year

        return tmdbApi.getMovies(parameters)
    }

    fun getMovies(queryParam: DiscoverQueryParam): Observable<DiscoverMovieResponse> {
        return Maybe.concat(getAllMoviesLocal(queryParam), getAllMoviesRemote(queryParam))
            .filter { movieResponse ->
                movieResponse.results.isNotEmpty() && isMovieUpToDate(movieResponse)
            }
            .take(1)
            .toObservable()
    }
    private fun isMovieUpToDate(movie: DiscoverMovieResponse) : Boolean {
        return movie.timestamp != 0L && System.currentTimeMillis() - movie.timestamp < Const.STALE_MS
    }

    fun moviesObservable(queryParam: DiscoverQueryParam): Observable<List<MovieResult>> {
        return getMoviesToDiscover(queryParam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap Observable.just(it.results)
            }
    }

    fun getVideos(id:Int) : Observable<VideoResponse>{
        return tmdbApi.getVideos(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDetails(id: Int): Observable<MovieDetails> {
        return tmdbApi.getMovieDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGenres(): Observable<List<Genre>> {
        return Maybe.concat(getAllGenresLocal(), getAllGenresRemote())
            .filter { list ->
                list.isNotEmpty()
            }
            .take(1)
            .toObservable()
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

    private fun getAllGenresRemote(): Maybe<List<Genre>> {
        return tmdbApi.getGenres()
            .flatMap {
                return@flatMap Maybe.just(it.genres)
            }.doAfterSuccess {
                saveData(it)
            }
            .doOnError {
                Log.i("Error", "Error")
            }
            .subscribeOn(Schedulers.io())
    }

    private fun saveData(data: List<Genre>) {
        genreDao.insert(data)
    }

    private fun getAllGenresLocal(): Maybe<List<Genre>> {
        return genreDao.getAllGenres()
    }

    fun getGenreById(genreId: Int): Single<Genre> {
        return genreDao.getGenre(genreId)
    }

    private fun getAllMoviesLocal(queryParam: DiscoverQueryParam): Maybe<DiscoverMovieResponse> {
        return moviesDao.getMovies(queryParam.genresId.toInt(), queryParam.page).doAfterSuccess{
            Log.i("Movies", it.toString())
        }
    }

}