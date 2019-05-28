package com.example.jvmori.discovermovies.data.repository.details

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.credits.Cast
import com.example.jvmori.discovermovies.data.network.response.credits.CreditsResponse
import com.example.jvmori.discovermovies.data.network.response.credits.Crew
import com.example.jvmori.discovermovies.data.network.response.movie.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.video.VideoResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private var tmdbAPI: TmdbAPI
) : DetailsRepository {

    private lateinit var connectableObservable: ConnectableObservable<CreditsResponse>

    override fun getDetails(id: Int): Observable<MovieDetails> {
        return tmdbAPI.getMovieDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getVideos(id: Int): Observable<VideoResponse> {
        return tmdbAPI.getVideos(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun setCreditsConnectable(movieId: Int) {
        connectableObservable = tmdbAPI.getCredits(movieId)
            .subscribeOn(Schedulers.io())
            .replay()
    }

    override fun connectToCredits() {
        connectableObservable.connect()
    }

    override fun getCast(): Single<List<Cast>> {
        return connectableObservable
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap Observable.just(it.cast)
            }
            .flatMapIterable { item -> item }
            .filter { it -> it.profilePath != null }
            .toList()
    }

    override fun getCrew(): Single<List<Crew>> {
        return connectableObservable
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap Observable.just(it.crew)
            }
            .flatMapIterable { item -> item }
            .filter { it -> it.profilePath != null }
            .toList()
    }

    override fun getRecommendations(movieId: Int): Observable<List<MovieResult>> {
        return tmdbAPI.getRecommendations(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap Observable.just(it.results)
            }
    }
}