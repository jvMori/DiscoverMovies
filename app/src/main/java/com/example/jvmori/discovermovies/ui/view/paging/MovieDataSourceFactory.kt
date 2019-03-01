package com.example.jvmori.discovermovies.ui.view.paging

import androidx.paging.DataSource
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam

class MovieDataSourceFactory(
    private val repository : MoviesRepository,
    private val parameters : DiscoverQueryParam
) : DataSource.Factory<Int, MovieResult>() {

    override fun create(): DataSource<Int, MovieResult> {
       return MoviesDataSource(repository, parameters)
    }
}