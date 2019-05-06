package com.example.jvmori.discovermovies.ui.presenter.genres

import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface GenresViewInterface : BaseViewInterface
{
    fun displayGenres(genreResponse: List<Genre>)
}