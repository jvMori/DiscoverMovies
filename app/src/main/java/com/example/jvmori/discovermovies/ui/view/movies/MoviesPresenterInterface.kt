package com.example.jvmori.discovermovies.ui.view.movies

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.jvmori.discovermovies.data.network.response.MovieResult


interface MoviesPresenterInterface {
    fun fetchMovies(parameters: DiscoverQueryParam)
    fun initMovies(parameters: DiscoverQueryParam)
}