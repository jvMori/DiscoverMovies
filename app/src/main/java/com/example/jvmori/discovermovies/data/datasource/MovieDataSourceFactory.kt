package com.example.jvmori.discovermovies.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.example.jvmori.discovermovies.data.repository.movies.MoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(
    private val repository: BaseMoviesRepository,
    private val parameters: DiscoverQueryParam?,
    private val disposable: CompositeDisposable
) : DataSource.Factory<Int, MovieResult>() {

    val newsDataSourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, MovieResult> {
        val moviesDataSource = MoviesDataSource(repository, parameters, disposable)
        newsDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }
}