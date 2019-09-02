package com.example.jvmori.discovermovies.ui.presenter.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

class TrendingPresenterImpl @Inject constructor(
    private val repository: BaseMoviesRepository
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
        val param = DiscoverQueryParam(period = period, genresId = Const.genreIdForTrendingMovies.toString(), page = 1)
        disposable.add(
            repository.getMovies(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showAllTrending(it.results)
                        view.hideProgressBar()
                    },
                    {
                        view.displayError("Error while loading data")
                        view.hideProgressBar()
                    }
                )
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