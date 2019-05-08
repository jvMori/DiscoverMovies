package com.example.jvmori.discovermovies.ui.presenter.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import javax.inject.Inject

class TrendingPresenterImpl @Inject constructor(
    private val repository: MoviesRepository
): TrendingPresenter {

    private val trending = MediatorLiveData<List<MovieResult>>()

    override fun fetchTrending(period: String, count: Long) {
        val source  = LiveDataReactiveStreams.fromPublisher(
            repository.getTrendingMovies("week", 3)
        )
        trending.addSource(source) {
            trending.postValue(it)
            trending.removeSource(source)
        }
    }

    override fun getTrending(): LiveData<List<MovieResult>> = trending
}