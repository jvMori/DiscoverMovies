package com.mori.jvmori.discovermovies.ui.presenter.search

import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.view.BaseViewInterface

interface SearchViewInterface : BaseViewInterface
{
    fun displayResults(results: List<MovieResult>)
    fun onQuerySubmit()
}