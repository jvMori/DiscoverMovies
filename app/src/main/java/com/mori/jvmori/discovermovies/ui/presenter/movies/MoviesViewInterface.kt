package com.mori.jvmori.discovermovies.ui.presenter.movies

import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.view.BaseViewInterface

interface MoviesViewInterface : BaseViewInterface
{
    fun displayAllItems(movieResponse: List<MovieResult>)
}