package com.example.jvmori.discovermovies.ui.view.discover

import android.annotation.SuppressLint
import android.util.Log
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.GenreResponse
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class GenresPresenter (
    var genresViewInterface: GenresViewInterface,
    private val repository: MoviesRepository
): GenresPresenterInterface {

    @SuppressLint("CheckResult")
    override fun getGenres() {
        getObservable().subscribeWith(getObserver())
    }

    private fun getObservable() : Observable<List<Genre>>{
        return repository.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { genresViewInterface.showProgressBar() }
    }

    private fun getObserver() : DisposableObserver<List<Genre>> {
        return object : DisposableObserver<List<Genre>>() {

            override fun onNext(response: List<Genre>) {
                genresViewInterface.displayGenres(response)
                //genresViewInterface.hideProgressBar()
            }

            override fun onError( e: Throwable) {
                e.printStackTrace()
                genresViewInterface.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d("Succes", "Completed")
                genresViewInterface.hideProgressBar()
            }
        }
    }
}