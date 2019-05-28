package com.example.jvmori.discovermovies.ui.presenter.search

import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.jvmori.discovermovies.data.repository.movies.MoviesRepository
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.disposables.CompositeDisposable
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
                        return@filter movie.mediaType == Const.MOVIE
                    }
                }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.i(TAG, it.toString())
                    view.displayResults(it)
                    view.hideProgressBar()
                }, {
                    Log.e(TAG, "onError: " + it.message)
                    view.displayError("onError: " + it.message)
                    view.hideProgressBar()
                }, {
                    Log.i(TAG, "completed")
                })
        )
    }

    override fun onSearchViewQueryChanged(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                view.onQuerySubmit()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i(TAG, "Search query: $newText")
                publishSubject.onNext(newText!!.toLowerCase().trim())
                view.showProgressBar()
                return true
            }
        })
    }
}