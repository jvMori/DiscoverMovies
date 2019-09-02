package com.example.jvmori.discovermovies.ui.presenter.movies


import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam


interface MoviesPresenterInterface {
    fun setView(view : MoviesViewInterface)
    val moviesDataList : LiveData<PagedList<MovieResult>>
    var parameters: DiscoverQueryParam?
    fun clear()
}