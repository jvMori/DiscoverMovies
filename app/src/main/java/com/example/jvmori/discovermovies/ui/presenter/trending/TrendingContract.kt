package com.example.jvmori.discovermovies.ui.presenter.trending

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.BasePresenter
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface TrendingContract {
    interface TrendingPresenter : BasePresenter {
        fun fetchTrending(period: String, count : Int)
    }
    interface TrendingView : BaseViewInterface {
        fun showResults(movies : List<MovieResult>)
    }
}