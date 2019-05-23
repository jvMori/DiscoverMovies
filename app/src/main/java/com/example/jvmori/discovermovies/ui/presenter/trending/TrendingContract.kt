package com.example.jvmori.discovermovies.ui.presenter.trending

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.BasePresenter
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface TrendingContract {
    interface TrendingPresenter : BasePresenter {
        fun getTrending(period :String, count : Int)
        fun chooseRandomMovies(count: Int, movies: List<MovieResult>): List<MovieResult>
    }
    interface TrendingView : BaseViewInterface {
        fun showRandomTrending(movies : List<MovieResult>)
        fun showAllTrending(movies: List<MovieResult>)
    }
}