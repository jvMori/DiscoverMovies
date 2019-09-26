package com.example.jvmori.discovermovies.ui.presenter.movies

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface MoviesViewInterface : BaseViewInterface
{
    fun displayAllItems(movieResponse: List<MovieResult>)
}