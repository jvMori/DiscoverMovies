package com.example.jvmori.discovermovies.ui.view.movies

import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MoviesPresenter (
    val moviesViewInterface: MoviesViewInterface,
    private val repository: MoviesRepository
) : MoviesPresenterInterface{

    override fun getMovies(parameters : DiscoverQueryParam) {
        getObservable(parameters).subscribeWith(getObserver())
    }

    private fun getObservable(parameters : DiscoverQueryParam) : Observable<DiscoverMovieResponse>{
        return repository.getMoviesToDiscover(parameters)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { moviesViewInterface.showProgressBar() }
    }

    private fun getObserver() : DisposableObserver<DiscoverMovieResponse>{
        return object : DisposableObserver<DiscoverMovieResponse>(){
            override fun onComplete() {
                moviesViewInterface.hideProgressBar()
            }

            override fun onNext(t: DiscoverMovieResponse) {
                moviesViewInterface.displayGenres(t)
            }

            override fun onError(e: Throwable) {
                moviesViewInterface.displayError("Error while loading data. Try again!")
            }
        }
    }
}