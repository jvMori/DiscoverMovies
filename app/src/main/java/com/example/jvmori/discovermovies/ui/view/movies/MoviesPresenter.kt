package com.example.jvmori.discovermovies.ui.view.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jvmori.discovermovies.MainActivity.Companion.TAG
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.data.datasource.MovieDataSourceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MoviesPresenter @Inject constructor(
    private val repository: MoviesRepository
) : MoviesPresenterInterface {

    private lateinit var view : MoviesViewInterface

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

    override fun fetchGenres() {
        disposable.add(
            repository.getAllGenresLocal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                       success -> view.displayGenres(success)
                    },
                    {
                        Log.i(TAG, "Error while fetching genres")
                    }
                )
        )
    }

    override fun clear() {
        disposable.clear()
    }
}