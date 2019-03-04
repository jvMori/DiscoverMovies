package com.example.jvmori.discovermovies.data.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MoviesDataSource(
    private val repository: MoviesRepository,
    private val parameters: DiscoverQueryParam,
    private val compositeDisposable: CompositeDisposable
) : ItemKeyedDataSource<Int, MovieResult>(
) {
    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    override fun getKey(item: MovieResult): Int {
        return item.id
    }

    private val firstPage: Int = parameters.page

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<MovieResult>) {
        compositeDisposable.add(
            repository.getMoviesToDiscover(DiscoverQueryParam(parameters.genresId, firstPage))
                .subscribe(
                    { movies ->
                        callback.onResult(movies.results)
                        networkState.postValue(NetworkState.LOADED)
                        initialLoad.postValue(NetworkState.LOADED)
                    },
                    { t: Throwable? ->
                        val error = NetworkState.error(t?.message)
                        networkState.postValue(error)
                        initialLoad.postValue(error)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<MovieResult>) {
        compositeDisposable.add(
            repository.getMoviesToDiscover(DiscoverQueryParam(parameters.genresId, params.key))
                .subscribe(
                    { movies ->
                        callback.onResult(movies.results)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    { t: Throwable? ->
                        networkState.postValue(NetworkState.error(t?.message))
                    }
                )
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<MovieResult>) {

    }
}