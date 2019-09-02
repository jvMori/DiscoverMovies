package com.example.jvmori.discovermovies.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSource(
    private val repository: BaseMoviesRepository,
    private val parameters: DiscoverQueryParam?,
    private val disposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieResult>(
) {
    private val firstPage: Int = parameters?.page ?: 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieResult>) {
        disposable.add(
            repository.getMovies(parameters!!)
                .subscribe(
                    { t ->
                        callback.onResult(t.results, null, firstPage + 1)
                    }, {
                        Log.i("Error", it.message)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        disposable.add(
            repository.getMovies(DiscoverQueryParam(parameters!!.genresId, params.key))
                .subscribe(
                    {
                        var key: Int? = null
                        if (params.key < it.totalPages) key = params.key + 1
                        callback.onResult(it.results, key)
                    }, {
                        Log.i("Error", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        disposable.add(
            repository.getMovies(DiscoverQueryParam(parameters!!.genresId, params.key))
                .subscribe(
                    {
                        var key: Int? = null
                        if (params.key > 1) key = params.key - 1
                        callback.onResult(it.results, key)
                    },
                    {
                        Log.i("Error", it.message)
                    }
                )
        )
    }
}