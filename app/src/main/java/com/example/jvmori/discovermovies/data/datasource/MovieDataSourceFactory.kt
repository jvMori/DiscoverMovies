package com.example.jvmori.discovermovies.data.datasource

import androidx.paging.DataSource
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(
    private val repository: MoviesRepository,
    private val parameters: DiscoverQueryParam,
    private val disposable: CompositeDisposable
) : DataSource.Factory<Int, MovieResult>() {

    override fun create(): DataSource<Int, MovieResult> {
        return MoviesDataSource(repository, parameters, disposable)
    }
}