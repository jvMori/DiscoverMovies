package com.example.jvmori.discovermovies.ui.presenter.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.trending.TrendingRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.random.Random

class TrendingPresenterImpl @Inject constructor(
    private val repository: TrendingRepository
) : TrendingContract.TrendingPresenter {

    private val disposable = CompositeDisposable()
    private val _trending = MutableLiveData<List<MovieResult>>()
    val trending: LiveData<List<MovieResult>> = _trending

    override fun dispose() {
        disposable.clear()
    }

    private lateinit var view: TrendingContract.TrendingView

    override fun <T> setView(view: T) {
        this.view = view as TrendingContract.TrendingView
    }

    override fun getTrending(period: String, count: Int) {
       // setConnectableTrending(period)
        fetchAllTrending(period)
       // repository.connectTrending()
    }

    private fun setConnectableTrending(period: String) {
       // repository.setConnectableTrendings(period)
    }

    private fun fetchAllTrending(period: String) {
        disposable.add(
            repository.fetchTrendingLocal(period)
                .subscribe({
                    if (it.isNotEmpty()) {
                        view.showAllTrending(it)
                        view.hideProgressBar()
                        checkIfRefreshNeeded(period, it)
                    } else {
                        fetchTrendingRemote(period)
                    }
                }, {
                    view.displayError("Error while loading data")
                    view.hideProgressBar()
                })
        )
    }

    private fun checkIfRefreshNeeded(period: String, oldTrending: List<MovieResult>) {
        if (oldTrending.isEmpty() || !repository.isTrendingMovieUpToDate(oldTrending[0]))
            fetchTrendingRemote(period)
    }

    private fun fetchTrendingRemote(period: String) {
        disposable.add(
            repository.fetchTrendingMoviesRemote(period)
                .subscribe({
                    view.showAllTrending(it.results)
                }, {
                    view.displayError("Something went wrong! Try again!")
                })
        )
    }

    override fun chooseRandomMovies(count: Int, movies: List<MovieResult>): List<MovieResult> {
        val newList = mutableListOf<MovieResult>()
        val randoms = mutableListOf<Int>()
        if (movies.isNotEmpty()) {
            for (x in 0..count) {
                chooseRandomInt(randoms, movies, newList)
            }
        }
        return newList
    }

    private fun chooseRandomInt(
        randoms: MutableList<Int>,
        movies: List<MovieResult>,
        movieList: MutableList<MovieResult>
    ) {
        val random = Random.nextInt(movies.size)
        if (!randoms.contains(random)) {
            randoms.add(random)
            movieList.add(movies[random])
        } else chooseRandomInt(randoms, movies, movieList)
    }

}