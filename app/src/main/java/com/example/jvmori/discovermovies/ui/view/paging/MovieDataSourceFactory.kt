package com.example.jvmori.discovermovies.ui.view.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam


class MovieDataSourceFactory(
    private val repository: MoviesRepository,
    private val parameters: DiscoverQueryParam
) : DataSource.Factory<Int, MovieResult>() {

    val moviesLiveData :MutableLiveData<PageKeyedDataSource<Int, MovieResult>> = MutableLiveData()

    override fun create(): DataSource<Int, MovieResult> {
        val dataSource = MoviesDataSource(repository, parameters)
        moviesLiveData.postValue(dataSource)
        return dataSource
    }
}