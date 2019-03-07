package com.example.jvmori.discovermovies.ui.view.details

import com.example.jvmori.discovermovies.data.network.response.MovieDetails
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface DetailsView  : BaseViewInterface{
    fun showResults(movieDetails: MovieDetails)
}