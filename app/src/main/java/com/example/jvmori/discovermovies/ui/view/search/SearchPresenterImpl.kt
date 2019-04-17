package com.example.jvmori.discovermovies.ui.view.search

import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.util.Const
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
    private lateinit var view: SearchViewInterface

    override fun clear() {
        disposable.clear()
    }

    override fun setView(view: SearchViewInterface) {
        this.view = view
    }

    override fun searchItems() {
        disposable.add(
            publishSubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter { return@filter !it.isEmpty() }
                .distinctUntilChanged()
                .observeOn(Schedulers.io())
                .switchMapSingle {
                    return@switchMapSingle repository.getSearchedItems(it)
                }
                .map {
                    it.filter { movie ->
                        return@filter movie.media_type == Const.MOVIE
                    }
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i(TAG, it.toString())
                    view.displayResults(it)
                }, {
                    Log.e(TAG, "onError: " + it.message)
                }, {
                    Log.i(TAG, "completed")
                })
        )
    }

    override fun onSearchViewQueryChanged(searchView: SearchView) {
        searchView
            .queryTextChangeEvents()
            .skipInitialValue()
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(searchContactsTextWatcher())
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
                Log.d(TAG, "Search query: completed")
                publishSubject.onComplete()
            }
        }
    }
}