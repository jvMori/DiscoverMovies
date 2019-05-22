package com.example.jvmori.discovermovies.ui.presenter.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.random.Random

class TrendingPresenterImpl @Inject constructor(
    private val repository: MoviesRepository
) : TrendingContract.TrendingPresenter {

    private val disposable = CompositeDisposable()
    private val _trending = MutableLiveData<List<MovieResult>>()
    val trending : LiveData<List<MovieResult>> = _trending

    override fun dispose() {
        disposable.clear()
    }

    private lateinit var view: TrendingContract.TrendingView

    override fun <T> setView(view: T) {
        this.view = view as TrendingContract.TrendingView
    }

    override fun fetchRandomTrending(period: String, count: Int)  {
        disposable.add(
            repository.getTrending(period)
                .flatMap { result ->
                    return@flatMap Observable.just(chooseRandomMovies(count, result))
                }
                .subscribe({
                    view.showRandomTrending(it)
                    view.hideProgressBar()
                }, {
                    view.displayError("Error while loading data")
                    view.hideProgressBar()
                })
        )

    }

    override fun fetchAllTrending(period: String) {
        disposable.add(
            repository.getTrending(period)
                .subscribe({
                    if (it.isNotEmpty()) {
                        view.showAllTrending(it)
                        view.hideProgressBar()
                    }
                }, {
                    view.displayError("Error while loading data")
                    view.hideProgressBar()
                })
        )
    }

    private fun chooseRandomMovies(count: Int, movies: List<MovieResult>): List<MovieResult> {
        val newList = mutableListOf<MovieResult>()
        val randoms = mutableListOf<Int>()
        if(movies.isNotEmpty()){
            for (x in 0..count) {
                chooseRandomInt(randoms, movies, newList)
            }
        }
        return newList
    }

    private fun chooseRandomInt(
        randoms: MutableList<Int>,
        movies: List<MovieResult>,
        movieList : MutableList<MovieResult>
    ) {
        val random = Random.nextInt(movies.size)
        if (!randoms.contains(random)) {
            randoms.add(random)
            movieList.add(movies[random])
        }
        else chooseRandomInt(randoms, movies, movieList)
    }

}