package com.example.jvmori.discovermovies.ui.view.search

import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.jvmori.discovermovies.data.network.response.movie.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.jakewharton.rxbinding3.appcompat.queryTextChangeEvents
import com.jakewharton.rxbinding3.appcompat.SearchViewQueryTextEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchPresenterImpl @Inject constructor(
    private val repository: MoviesRepository
) : SearchPresenter {

    val TAG = "DISCOVER_MOVIES"
    private val disposable = CompositeDisposable()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()
    private val searchObserver: DisposableObserver<List<MovieResult>> = getSearchObserver()

    override fun clear() {
        disposable.clear()
    }

    override fun searchItems(query: String) {
        disposable.add(
            publishSubject.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle {
                    return@switchMapSingle repository.getSearchedItems(query)
                }
                .subscribeWith(searchObserver)
        )
    }

    override fun searchViewQueryChanged(searchView: SearchView) {
        disposable.add(
            searchView.queryTextChangeEvents()
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher())
        )
    }

    private fun searchContactsTextWatcher(): DisposableObserver<SearchViewQueryTextEvent> {
        return object : DisposableObserver<SearchViewQueryTextEvent>() {
            override fun onNext(searchViewQueryTextEvent: SearchViewQueryTextEvent) {
                Log.d(TAG, "Search query: " + searchViewQueryTextEvent.queryText)
                publishSubject.onNext(searchViewQueryTextEvent.queryText.toString())
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: " + e.message)
            }

            override fun onComplete() {

            }
        }
    }

    private fun getSearchObserver(): DisposableObserver<List<MovieResult>> {
        return object : DisposableObserver<List<MovieResult>>() {
            override fun onComplete() {

            }

            override fun onNext(t: List<MovieResult>) {
                Log.i(TAG, t.toString())
            }

            override fun onError(e: Throwable) {

            }

        }
    }
}