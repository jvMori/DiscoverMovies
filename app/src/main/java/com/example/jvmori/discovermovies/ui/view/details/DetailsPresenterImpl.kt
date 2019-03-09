package com.example.jvmori.discovermovies.ui.view.details

import android.annotation.SuppressLint
import android.util.Log
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsPresenterImpl @Inject constructor(
    private var repository: MoviesRepository
) : DetailsPresenter {
    private lateinit var view: DetailsView
    private val disposable = CompositeDisposable()

    override fun setView(view: DetailsView) {
        this.view = view
    }

    override fun fetchDetails(id: Int) {
        view.showProgressBar()
        disposable.add(
            repository.getDetails(id)
                .subscribe({
                    view.showResults(it)
                }, {
                    Log.i("Error", it.message)
                    view.displayError("Error while fetching data! Try again!")
                },{
                    view.hideProgressBar()
                })
        )
    }

    override fun fetchVideo(movieId: Int) {

    }

    override fun onClear() {
        //disposable.dispose()
    }
}