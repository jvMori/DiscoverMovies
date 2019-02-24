package com.example.jvmori.discovermovies.ui.view.discover

import android.util.Log
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.GenreResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class GenresPresenter (
    var genresViewInterface: GenresViewInterface
): GenresPresenterInterface {

    override fun getGenres() {
        getObservable().subscribeWith(getObserver())
    }

    private fun getObservable() : Observable<GenreResponse>{
        return TmdbAPI.invoke().getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { genresViewInterface.showProgressBar() }
    }

    private fun getObserver() : DisposableObserver<GenreResponse> {
        return object : DisposableObserver<GenreResponse>() {

            override fun onNext(response: GenreResponse) {
                Log.d("Error", "OnNext" + response.genres)
                genresViewInterface.displayGenres(response)
            }

            override fun onError( e: Throwable) {
                Log.d("error", "Error$e")
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