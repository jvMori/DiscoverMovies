package com.example.jvmori.discovermovies.ui.view.details

interface DetailsPresenter {
    fun fetchDetails(id : Int)
    fun fetchVideo(movieId : Int)
    fun setView(view : DetailsView)
    fun onClear()
}