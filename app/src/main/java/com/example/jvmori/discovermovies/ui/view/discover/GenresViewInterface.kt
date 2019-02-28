package com.example.jvmori.discovermovies.ui.view.discover

import com.example.jvmori.discovermovies.data.network.response.Genre
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface GenresViewInterface : BaseViewInterface
{
    fun displayGenres(genreResponse: List<Genre>)
}