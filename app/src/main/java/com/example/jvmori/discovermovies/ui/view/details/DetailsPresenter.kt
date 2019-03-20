package com.example.jvmori.discovermovies.ui.view.details

interface DetailsPresenter {
    fun fetchDetails(id : Int)
    fun fetchVideo(movieId : Int)
    fun createConnectableCredits(movieId: Int)
    fun connectToCreditsObservable()
    fun fetchCast()
    fun fetchCrew()
    fun setView(view : DetailsView)
    fun onClear()
}