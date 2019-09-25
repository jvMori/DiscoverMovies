package com.mori.jvmori.discovermovies.ui.presenter.movies

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mori.jvmori.discovermovies.data.datasource.MovieDataSourceFactory
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.mori.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
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
    override var parameters: DiscoverQueryParam? = null
    private val disposable = CompositeDisposable()
    private lateinit var sourceFactory : MovieDataSourceFactory

    override val moviesDataList: LiveData<PagedList<MovieResult>> by lazy {
        sourceFactory =
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