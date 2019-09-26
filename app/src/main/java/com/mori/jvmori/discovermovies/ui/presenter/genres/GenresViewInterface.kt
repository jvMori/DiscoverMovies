package com.mori.jvmori.discovermovies.ui.presenter.genres

import com.mori.jvmori.discovermovies.data.local.entity.Genre
import com.mori.jvmori.discovermovies.ui.view.BaseViewInterface

interface GenresViewInterface : BaseViewInterface
{
    fun displayGenres(genreResponse: List<Genre>)
}