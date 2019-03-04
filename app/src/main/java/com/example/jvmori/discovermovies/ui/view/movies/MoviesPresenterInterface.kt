package com.example.jvmori.discovermovies.ui.view.movies


import com.example.jvmori.discovermovies.data.local.entity.Genre
import io.reactivex.Single


interface MoviesPresenterInterface {
    fun fetchMovies(parameters: DiscoverQueryParam)
    fun fetchGenreById(id : Int) : Single<Genre>
}