package com.example.jvmori.discovermovies.ui.view.movies

import com.example.jvmori.discovermovies.data.network.response.MovieResult
import io.reactivex.disposables.Disposable
import io.reactivex.observables.ConnectableObservable

interface MoviesPresenterInterface {
    fun getDetailsForEachMovie(parameters: DiscoverQueryParam) : Disposable

    fun getContactableObservable(parameters: DiscoverQueryParam) : ConnectableObservable<List<MovieResult>>

    fun fetchAllMovies(parameters : DiscoverQueryParam) : Disposable

    fun fetchMovies(parameters: DiscoverQueryParam)
}