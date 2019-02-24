package com.example.jvmori.discovermovies.ui.view.movies

import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse

interface MoviesViewInterface
{
    fun showProgressBar()
    fun hideProgressBar()
    fun displayGenres(movieResponse: DiscoverMovieResponse)
    fun displayError(s: String)
}