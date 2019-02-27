package com.example.jvmori.discovermovies.ui.view.movies


interface MoviesPresenterInterface {
    fun fetchMovies(parameters: DiscoverQueryParam)
}