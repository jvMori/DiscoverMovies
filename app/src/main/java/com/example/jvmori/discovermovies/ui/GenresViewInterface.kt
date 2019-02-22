package com.example.jvmori.discovermovies.ui

import com.example.jvmori.discovermovies.data.network.response.GenreResponse

interface GenresViewInterface
{
    fun showProgressBar()
    fun hideProgressBar()
    fun displayGenres(genreResponse: GenreResponse)
    fun displayError(s: String)
}