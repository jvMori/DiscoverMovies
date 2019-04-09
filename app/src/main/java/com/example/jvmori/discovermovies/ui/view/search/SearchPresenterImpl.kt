package com.example.jvmori.discovermovies.ui.view.search

import androidx.appcompat.widget.SearchView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchPresenterImpl : SearchPresenter {

    private val disposable = CompositeDisposable()
    private val publishSubject : PublishSubject<String> = PublishSubject.create()

    override fun clear() {

    }

    override fun searchItems(query: String) {
//        disposable.add(
//            publishSubject.debounce(300, TimeUnit.MILLISECONDS)
//                .distinctUntilChanged()
//                .switchMapSingle{
//                    return@switchMapSingle
//                }
//        )
    }

    override fun searchViewQueryChanged(searchView: SearchView) {

    }
}