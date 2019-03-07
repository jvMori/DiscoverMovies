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
        disposable.add(
            repository.getDetails(id)
                .subscribe({
                    Log.i("Succes", it.toString())
                }, {
                    Log.i("Error", it.message)
                })
        )
    }

    override fun onClear() {
        disposable.dispose()
    }
}