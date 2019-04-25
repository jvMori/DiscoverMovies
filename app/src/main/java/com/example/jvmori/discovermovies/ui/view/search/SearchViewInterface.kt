package com.example.jvmori.discovermovies.ui.view.search

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface SearchViewInterface : BaseViewInterface
{
    fun displayResults(results: List<MovieResult>)
    fun onQuerySubmit()
}