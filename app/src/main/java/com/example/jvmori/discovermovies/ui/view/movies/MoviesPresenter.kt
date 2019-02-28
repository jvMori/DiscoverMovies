package com.example.jvmori.discovermovies.ui.view.movies


import android.os.Looper
import android.util.Log
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableFromIterable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.observables.ConnectableObservable


class MoviesPresenter(
    val moviesViewInterface: MoviesViewInterface,
    private val repository: MoviesRepository
) : MoviesPresenterInterface {

    override fun fetchMovies(parameters: DiscoverQueryParam) {
       val observableMovies : ConnectableObservable<List<MovieResult>> = repository.moviesObservable(parameters).replay()
        observableMovies
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                getObserverForAllItems()
            )

        observableMovies
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap ObservableFromIterable(it)
            }
            .flatMap {
                return@flatMap repository.getDetails(it)
            }
            .subscribe(
                getMovieResultObserver()
            )

        observableMovies.connect()
    }

    private fun getMovieResultObserver(): DisposableObserver<MovieResult> {
        return object : DisposableObserver<MovieResult>() {
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

    private fun getObserverForAllItems(): DisposableObserver<List<MovieResult>> {
        return object : DisposableObserver<List<MovieResult>>() {
            override fun onComplete() {

            }

            override fun onNext(t: List<MovieResult>) {
                moviesViewInterface.displayAllItems(t)
            }

            override fun onError(e: Throwable) {
                moviesViewInterface.displayError("Error while loading data. Try again!" + e.message)
            }
        }
    }
}