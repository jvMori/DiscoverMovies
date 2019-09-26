package com.mori.jvmori.discovermovies.ui.presenter.genres

import android.annotation.SuppressLint
import android.util.Log
import com.mori.jvmori.discovermovies.data.local.entity.Genre
import com.mori.jvmori.discovermovies.data.repository.genres.GenresRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GenresPresenter @Inject constructor (
    private val repository: GenresRepository
) : GenresPresenterInterface {

    private lateinit var genresViewInterface: GenresViewInterface

    override fun setView(genresViewInterface: GenresViewInterface) {
        this.genresViewInterface = genresViewInterface
    }

    @SuppressLint("CheckResult")
    override fun getGenres() {
        getObservable().subscribe(getObserver())
    }

    private fun getObservable(): Observable<List<Genre>> {
        return repository.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): DisposableObserver<List<Genre>> {
        return object : DisposableObserver<List<Genre>>() {

            override fun onNext(response: List<Genre>) {
                genresViewInterface.displayGenres(response)
                //genresViewInterface.hideProgressBar()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                genresViewInterface.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d("Succes", "Completed")
                //genresViewInterface.hideProgressBar()
            }
        }
    }
}