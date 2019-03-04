package com.example.jvmori.discovermovies.ui.view.movies


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.data.datasource.MovieDataSourceFactory
import com.example.jvmori.discovermovies.data.local.entity.Genre
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.observable.ObservableFromIterable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.observables.ConnectableObservable


class MoviesPresenter(
    val moviesViewInterface: MoviesViewInterface,
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

    @SuppressLint("CheckResult")
    override fun fetchMovies(parameters: DiscoverQueryParam) {
       val observableMovies : ConnectableObservable<List<MovieResult>> = repository.moviesObservable(parameters).replay()

        observableMovies
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                getObserverForAllItems()
            )

        observableMovies.connect()
    }
    @SuppressLint("CheckResult")
    fun fetchDetails(data : List<MovieResult>){
       Observable.just(data)
            .flatMap {
                return@flatMap ObservableFromIterable(it)
            }
            .flatMap {
                return@flatMap repository.getDetails(it)
            }
            .subscribeWith(
                getMovieResultObserver()
            )
    }

    override fun fetchGenreById(id : Int) : Single<Genre>{
        return repository.getGenreById(id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    private fun getMovieResultObserver(): DisposableObserver<MovieResult> {
        return object : DisposableObserver<MovieResult>() {
            override fun onComplete() {
                moviesViewInterface.hideProgressBar()
            }

            override fun onNext(t: MovieResult) {
                moviesViewInterface.setMovieDetails(t)
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

    override fun clear() {
        disposable.dispose()
    }
}