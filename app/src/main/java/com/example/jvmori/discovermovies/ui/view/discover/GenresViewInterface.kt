package com.example.jvmori.discovermovies.ui.view.discover

import com.example.jvmori.discovermovies.data.network.response.GenreResponse
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface GenresViewInterface : BaseViewInterface
{
    fun displayGenres(genreResponse: GenreResponse)
}