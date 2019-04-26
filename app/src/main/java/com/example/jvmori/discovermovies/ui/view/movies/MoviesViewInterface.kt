package com.example.jvmori.discovermovies.ui.view.movies

import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface MoviesViewInterface : BaseViewInterface
{
    fun displayAllItems(movieResponse: List<MovieResult>)
    fun displayGenres(genres: List<Genre>)
    fun onMovieSaved()
}