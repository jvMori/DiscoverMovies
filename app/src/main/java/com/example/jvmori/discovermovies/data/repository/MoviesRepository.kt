package com.example.jvmori.discovermovies.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.response.credits.Cast
import com.example.jvmori.discovermovies.data.network.response.credits.CreditsResponse
import com.example.jvmori.discovermovies.data.network.response.credits.Crew
import com.example.jvmori.discovermovies.data.network.response.movie.MovieDetails
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.response.video.VideoResponse
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val tmdbApi: TmdbAPI,
    context: Context
) {
    private val genreDao = MovieDatabase.invoke(context.applicationContext).genreDao()
    private val moviesDao = MovieDatabase.invoke(context.applicationContext).moviesDao()
    private val savedMovieDao = MovieDatabase.invoke(context.applicationContext).savedMovieDao()
    private lateinit var connectableObservable: ConnectableObservable<CreditsResponse>

    fun getMovies(queryParam: DiscoverQueryParam): Observable<DiscoverMovieResponse> {
        return Maybe.concat(getAllMoviesLocal(queryParam), getAllMoviesRemote(queryParam))
            .filter { movieResponse ->
                movieResponse.results.isNotEmpty() && isMovieUpToDate(movieResponse)
            }
            .take(1)
            .toObservable()
    }

    fun getVideos(id: Int): Observable<VideoResponse> {
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

    fun getGenreById(genreId: Int): Single<Genre> {
        return genreDao.getGenre(genreId)
    }

    fun setCreditsConnectable(movieId: Int) {
        connectableObservable = tmdbApi.getCredits(movieId)
            .subscribeOn(Schedulers.io())
            .replay()
    }

    fun connectToCredits() {
        connectableObservable.connect()
    }

    fun getCast(): Single<List<Cast>> {
        return connectableObservable
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap Observable.just(it.cast)
            }
            .flatMapIterable { item -> item }
            .filter { it -> it.profilePath != null }
            .toList()
    }

    fun getCrew(): Single<List<Crew>> {
        return connectableObservable
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap Observable.just(it.crew)
            }
            .flatMapIterable { item -> item }
            .filter { it -> it.profilePath != null }
            .toList()
    }

    fun getRecommendations(movieId: Int): Observable<List<MovieResult>> {
        return tmdbApi.getRecommendations(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap Observable.just(it.results)
            }
    }

    fun getSearchedItems(q: String): Single<List<MovieResult>> {
        return tmdbApi.getSearchedItems(q)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap Single.just(it.results)
            }
    }

    fun handleFavClick(movie: MovieResult) : Single<MovieResult>{
        return savedMovieDao.getMovie(movie.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun deleteMovie(movie: MovieResult) {
        Completable.fromAction { savedMovieDao.delete(movie) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun saveMovie(movie: MovieResult) {
        movie.mediaType = "" //can't be null in sql database
        Completable.fromAction { savedMovieDao.insert(movie) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun displayAllSaved(): LiveData<List<MovieResult>> {
        return LiveDataReactiveStreams.fromPublisher {
            savedMovieDao.getAllSaved()
                .subscribeOn(Schedulers.io())
        }
    }

    fun getTrendingMovies(period: String, count : Long) : Flowable<List<MovieResult>>{
        return tmdbApi.getTrendingMovies(period)
            .flatMap {
                return@flatMap Flowable.just(it.results)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(count)
    }

    private fun getMoviesToDiscover(queryParam: DiscoverQueryParam): Observable<DiscoverMovieResponse> {
        val parameters: HashMap<String, String> = HashMap()
        parameters["sort_by"] = "popularity.desc"
        parameters["page"] = queryParam.page.toString()
        parameters["vote_average.gte"] = queryParam.vote.toString()
        parameters["with_genres"] = queryParam.genresId
        parameters["year"] = queryParam.year

        return tmdbApi.getMovies(parameters)
    }

    private fun isMovieUpToDate(movie: DiscoverMovieResponse): Boolean {
        return movie.timestamp != 0L && System.currentTimeMillis() - movie.timestamp < Const.STALE_MS
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

    private fun getAllMoviesLocal(queryParam: DiscoverQueryParam): Maybe<DiscoverMovieResponse> {
        return moviesDao.getMovies(queryParam.genresId.toInt(), queryParam.page).doAfterSuccess {
            Log.i("Movies", it.toString())
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

    public fun getAllGenresLocal(): Maybe<List<Genre>> {
        return genreDao.getAllGenres()
    }

    private fun saveData(data: List<Genre>) {
        Completable.fromAction {
            genreDao.insert(data)
        }.subscribeOn(Schedulers.io())
            .subscribe()

    }

}