package com.example.jvmori.discovermovies.ui.presenter.movies

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jvmori.discovermovies.data.datasource.MovieDataSourceFactory
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class MoviesPresenter @Inject constructor(
    private val repository: BaseMoviesRepository
) : MoviesPresenterInterface{

    private lateinit var view: MoviesViewInterface

    override fun setView(view: MoviesViewInterface) {
        this.view = view
    }

    private val pageSize = 20
    override lateinit var parameters: DiscoverQueryParam
    private val disposable = CompositeDisposable()

    override val moviesDataList: LiveData<PagedList<MovieResult>> by lazy {
        val sourceFactory =
            MovieDataSourceFactory(repository, parameters, disposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        LivePagedListBuilder<Int, MovieResult>(sourceFactory, config).build()
    }

    override fun clear() {
        disposable.clear()
    }
}