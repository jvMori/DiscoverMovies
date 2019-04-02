package com.example.jvmori.discovermovies.ui.view.movies

import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.response.movie.MovieResult
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface MoviesViewInterface : BaseViewInterface
{
    fun setMovieDetails(movieResult : MovieResult)
    fun displayAllItems(movieResponse: List<MovieResult>)
    fun displayGenres(genres: Genre)
}