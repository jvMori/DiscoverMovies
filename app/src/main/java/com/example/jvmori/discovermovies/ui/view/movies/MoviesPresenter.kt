package com.example.jvmori.discovermovies.ui.view.movies

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.data.datasource.MovieDataSourceFactory
import com.example.jvmori.discovermovies.data.local.entity.Genre
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MoviesPresenter @Inject constructor (
    private val repository: MoviesRepository
) : MoviesPresenterInterface {

    private val pageSize = 20
    override lateinit var parameters: DiscoverQueryParam
    private val disposable = CompositeDisposable()

    override val moviesDataList : LiveData<PagedList<MovieResult>> by lazy {
        val sourceFactory =
            MovieDataSourceFactory(repository, parameters, disposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        LivePagedListBuilder<Int, MovieResult>(sourceFactory, config).build()
    }

    override fun fetchGenreById(id : Int) : Single<Genre>{
        return repository.getGenreById(id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    override fun clear() {
        disposable.dispose()
    }
}