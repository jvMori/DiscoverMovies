package com.example.jvmori.discovermovies.ui.presenter.trending

import androidx.lifecycle.LiveData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult

interface TrendingPresenter {
    fun fetchTrending(period: String, count : Long)
    fun getTrending() : LiveData<List<MovieResult>>
}