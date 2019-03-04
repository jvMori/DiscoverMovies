package com.example.jvmori.discovermovies.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(
    private val repository: MoviesRepository,
    private val parameters: DiscoverQueryParam,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieResult>() {

    val moviesDataSourceLivaData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, MovieResult> {
        val dataSource = MoviesDataSource(
            repository,
            parameters,
            compositeDisposable
        )
        moviesDataSourceLivaData.postValue(dataSource)
        return dataSource
    }
}