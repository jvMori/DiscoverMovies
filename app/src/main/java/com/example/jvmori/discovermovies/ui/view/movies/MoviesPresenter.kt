package com.example.jvmori.discovermovies.ui.view.movies


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.view.paging.MovieDataSourceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableFromIterable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.observables.ConnectableObservable


class MoviesPresenter(
    val moviesViewInterface: MoviesViewInterface,
    private val repository: MoviesRepository
) : MoviesPresenterInterface {

    private lateinit var movieDataSource : LiveData<PageKeyedDataSource<Int, MovieResult>>
    private val pageSize = 20

    override fun initMovies(parameters: DiscoverQueryParam) : LiveData<PagedList<MovieResult>>{
        val sourceFactory = MovieDataSourceFactory(repository, parameters)
        movieDataSource = sourceFactory.moviesLiveData

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()

        return (LivePagedListBuilder<Int, MovieResult>(sourceFactory, config)).build()
    }



    @SuppressLint("CheckResult")
    override fun fetchMovies(parameters: DiscoverQueryParam) {
       val observableMovies : ConnectableObservable<List<MovieResult>> = repository.moviesObservable(parameters).replay()

        observableMovies
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                getObserverForAllItems()
            )

        observableMovies
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap ObservableFromIterable(it)
            }
            .flatMap {
                return@flatMap repository.getDetails(it)
            }
            .subscribeWith(
                getMovieResultObserver()
            )

        observableMovies.connect()
    }

    private fun getMovieResultObserver(): DisposableObserver<MovieResult> {
        return object : DisposableObserver<MovieResult>() {
            override fun onComplete() {
                moviesViewInterface.hideProgressBar()
            }

            override fun onNext(t: MovieResult) {
                moviesViewInterface.displayMovie(t)
            }

            override fun onError(e: Throwable) {
                moviesViewInterface.displayError("Error while loading data. Try again!" + e.message)
            }
        }
    }

    private fun getObserverForAllItems(): DisposableObserver<List<MovieResult>> {
        return object : DisposableObserver<List<MovieResult>>() {
            override fun onComplete() {

            }

            override fun onNext(t: List<MovieResult>) {
                moviesViewInterface.displayAllItems(t)
            }

            override fun onError(e: Throwable) {
                moviesViewInterface.displayError("Error while loading data. Try again!" + e.message)
            }
        }
    }
}