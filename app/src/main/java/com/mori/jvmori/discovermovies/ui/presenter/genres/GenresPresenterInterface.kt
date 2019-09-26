package com.mori.jvmori.discovermovies.ui.presenter.genres


interface GenresPresenterInterface {
    fun getGenres()
    fun setView(genresViewInterface: GenresViewInterface)
}