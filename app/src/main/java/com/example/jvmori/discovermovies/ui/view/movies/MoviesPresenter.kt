package com.example.jvmori.discovermovies.ui.view.movies

import android.annotation.SuppressLint
import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.response.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.internal.operators.observable.ObservableFromIterable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.observables.ConnectableObservable


class MoviesPresenter(
    val moviesViewInterface: MoviesViewInterface,
    private val repository: MoviesRepository
) : MoviesPresenterInterface {


    @SuppressLint("CheckResult")
    override fun getMovies(parameters: DiscoverQueryParam) {
//        var moviesObservable = repository.getMoviesToDiscover (parameters).replay()
//        moviesObservable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//        getObservable(parameters).subscribeWith(getObserver())

        val movies: ConnectableObservable<List<MovieResult>> = repository.moviesObservable(parameters).replay()
        movies.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap ObservableFromIterable(it)
            }
            .flatMap {
                return@flatMap repository.getDetails(it)
            }
            .subscribeWith(
                getMovieResultObserver()
            )
    }

    private fun getObservable(parameters: DiscoverQueryParam): Observable<DiscoverMovieResponse> {
        return repository.getMoviesToDiscover(parameters)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { moviesViewInterface.showProgressBar() }
    }

    private fun getMovieResultObserver(): DisposableObserver<MovieResult>{
        return object : DisposableObserver<MovieResult>(){
            override fun onComplete() {
                moviesViewInterface.hideProgressBar()
            }

            override fun onNext(t: MovieResult) {
              moviesViewInterface.displayMovie(t)
            }

            override fun onError(e: Throwable) {
                moviesViewInterface.displayError("Error while loading data. Try again!" + e.message)
            }

        }
    }

    private fun getObserver(): DisposableObserver<DiscoverMovieResponse> {
        return object : DisposableObserver<DiscoverMovieResponse>() {
            override fun onComplete() {
                moviesViewInterface.hideProgressBar()
            }

            override fun onNext(t: DiscoverMovieResponse) {
                moviesViewInterface.displayItems(t)
            }

            override fun onError(e: Throwable) {
                moviesViewInterface.displayError("Error while loading data. Try again!" + e.message)
            }
        }
    }
}