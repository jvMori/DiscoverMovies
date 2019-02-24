package com.example.jvmori.discovermovies.ui.view.movies

data class DiscoverQueryParam (
    val genresId: String = "",
    val page: Int,
    val vote: Int = 5,
    val year: String = ""
)
