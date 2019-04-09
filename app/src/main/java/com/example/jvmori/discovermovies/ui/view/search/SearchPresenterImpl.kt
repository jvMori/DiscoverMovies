package com.example.jvmori.discovermovies.ui.view.search

import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.jvmori.discovermovies.data.network.response.movie.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.jakewharton.rxbinding3.widget.queryTextChangeEvents

class SearchPresenterImpl @Inject constructor  (
    private val repository: MoviesRepository
): SearchPresenter {

    val TAG = "DISCOVER_MOVIES"
    private val disposable = CompositeDisposable()
    private val publishSubject : PublishSubject<String> = PublishSubject.create()
    private val searchObserver : DisposableObserver<List<MovieResult>> = getSearchObserver()

    override fun clear() {

    }

    override fun searchItems(query: String) {
        disposable.add(
            publishSubject.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle{
                    return@switchMapSingle repository.getSearchedItems(query)
                }
                .subscribeWith(searchObserver)
        )
    }

    override fun searchViewQueryChanged(searchView: SearchView) {
      
    }

    private fun getSearchObserver() : DisposableObserver<List<MovieResult>>{
        return object : DisposableObserver<List<MovieResult>>(){
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