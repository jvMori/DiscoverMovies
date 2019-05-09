package com.example.jvmori.discovermovies.ui.presenter.trending

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.random.Random

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

    override fun fetchTrending(period: String, count: Int) {
        disposable.add(
            repository.getTrending("week")
                .flatMap{ result ->
                    return@flatMap Flowable.just(chooseRandomMovies(count, result))
                }
                .subscribe({
                    view.showResults(it)
                    view.hideProgressBar()
                }, {
                    view.displayError("Error while loading data")
                    view.hideProgressBar()
                })
        )
    }
    private fun chooseRandomMovies(count: Int, movies : List<MovieResult>) : List<MovieResult>{
        val newList = mutableListOf<MovieResult>()
        for (x in 0..count) {
            val random = Random.nextInt(movies.size)
            newList.add(movies[random])
        }
        return newList
    }
}