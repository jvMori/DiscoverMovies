package com.example.jvmori.discovermovies.ui.presenter.details

interface DetailsPresenter {
    fun fetchDetails(id : Int)
    fun fetchVideo(movieId : Int)
    fun createConnectableCredits(movieId: Int)
    fun connectToCreditsObservable()
    fun fetchCast()
    fun fetchCrew()
    fun fetchRecommendations(movieId: Int)
    fun setView(view : DetailsView)
    fun onClear()
}