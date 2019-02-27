package com.example.jvmori.discovermovies.ui.view.movies

import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface MoviesViewInterface : BaseViewInterface
{
    fun displayMovie(movieResult : MovieResult)
    fun displayAllItems(movieResponse: List<MovieResult>)
}