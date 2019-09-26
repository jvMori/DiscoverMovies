package com.mori.jvmori.discovermovies.ui.presenter.details

import android.util.Log
import com.mori.jvmori.discovermovies.data.repository.details.DetailsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsPresenterImpl @Inject constructor(
    private var repository: DetailsRepository
) : DetailsPresenter {

    private lateinit var view: DetailsView
    private val disposable = CompositeDisposable()

    override fun <T> setView(view: T) {
        this.view = view as DetailsView
    }

    override fun dispose() {
        disposable.clear()
    }

    override fun getDetails(id: Int){
        fetchDetails(id)
        fetchVideo(id)
        createConnectableCredits(id)
        fetchCast()
        fetchCrew()
        fetchRecommendations(id)
        connectToCreditsObservable()
    }


    private fun fetchDetails(id: Int) {
        view.showProgressBar()
        disposable.add(
            repository.getDetails(id)
                .subscribe({
                    view.showResults(it)
                }, {
                    Log.i("Error", it.message)
                    view.displayError("Error while fetching data! Try again!")
                }, {
                    view.hideProgressBar()
                })
        )
    }

    private fun fetchVideo(movieId: Int) {
        disposable.add(
            repository.getVideos(movieId)
                .subscribe({
                    view.getVideo(it)
                }, {
                    view.displayError("Error while fetching data! Try again!")
                })
        )
    }

    private fun fetchCast() {
        disposable.add(
            repository.getCast()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    view.showCast(result)
                }, {
                    view.displayError("Error while fetching data! Try again!")
                })
        )
    }

    private fun fetchCrew() {
        disposable.add(
            repository.getCrew()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showCrew(it)
                }, {
                    view.displayError("Error while fetching data! Try again!")
                })
        )
    }

    private fun fetchRecommendations(movieId: Int) {
        disposable.add(
            repository.getRecommendations(movieId)
                .subscribe({
                    view.showRecommendations(it)
                }, {
                    view.displayError("Error while downloading similar movies")
                })
        )
    }

    private fun createConnectableCredits(movieId: Int) {
        repository.setCreditsConnectable(movieId)
    }

    private fun connectToCreditsObservable() {
        repository.connectToCredits()
    }
}