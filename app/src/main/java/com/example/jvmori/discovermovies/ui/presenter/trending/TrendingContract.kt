package com.example.jvmori.discovermovies.ui.presenter.trending

import androidx.lifecycle.LiveData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.BasePresenter
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface TrendingContract {
    interface TrendingPresenter : BasePresenter {
        fun fetchRandomTrending(period: String, count : Int)
        fun fetchAllTrending(period: String)
    }
    interface TrendingView : BaseViewInterface {
        fun showRandomTrending(movies : List<MovieResult>)
        fun showAllTrending(movies: List<MovieResult>)
    }
}