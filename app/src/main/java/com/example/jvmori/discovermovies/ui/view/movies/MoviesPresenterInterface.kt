package com.example.jvmori.discovermovies.ui.view.movies


import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import io.reactivex.Single


interface MoviesPresenterInterface {
    fun fetchMovies(parameters: DiscoverQueryParam)
    fun fetchGenreById(id : Int) : Single<Genre>
    val moviesDataList : LiveData<PagedList<MovieResult>>
    var parameters: DiscoverQueryParam
    fun clear()
}