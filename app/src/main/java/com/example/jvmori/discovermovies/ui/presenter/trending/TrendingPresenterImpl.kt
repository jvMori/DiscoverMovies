package com.example.jvmori.discovermovies.ui.presenter.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.example.jvmori.discovermovies.MainActivity
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TrendingPresenterImpl @Inject constructor(
    private val repository: MoviesRepository
) : TrendingContract.TrendingPresenter {

    private val disposable = CompositeDisposable()

    override fun dispose() {
        disposable.clear()
    }

    private lateinit var view: TrendingContract.TrendingView

    override fun <T> setView(view: T) {
        this.view = view as TrendingContract.TrendingView
    }

    override fun fetchTrending(period: String, count: Long) {
        view.showProgressBar()
        disposable.add(
            repository.getTrendingMovies("week", 3)
                .subscribe({
                    view.showResults(it)
                    view.hideProgressBar()
                }, {
                    view.displayError("Error while loading data")
                    view.hideProgressBar()
                })
        )
    }
}