package com.mori.jvmori.discovermovies.ui.view.movies

data class DiscoverQueryParam (
    val genresId: String = "",
    val page: Int = 1,
    val vote: Int = 5,
    val year: String = "",
    val period : String = "week"
)
