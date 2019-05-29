package com.example.jvmori.discovermovies.ui.presenter.nowPlaying

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.nowPlaying.NowPlayingRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingPresenterImpl @Inject constructor(
    private val repository: NowPlayingRepository
) : NowPlayingContract.NowPlayingPresenter {

    private val disposable = CompositeDisposable()
    private lateinit var view: NowPlayingContract.NowPlayingView

    override fun fetchNowPlaying() {
        disposable.add(
            repository.getNowPlayingLocal()
                .subscribe(
                    {
                        if (it.isNotEmpty()) {
                            view.hideProgressBar()
                            view.showNowPlaying(it)
                            checkIfRefreshNeeded(it)
                        } else {
                            fetchRemote()
                        }
                    },
                    {
                        view.hideProgressBar()
                        view.displayError("Error while loading now playing movies")
                    }
                )
        )
    }

    private fun fetchRemote() {
        disposable.add(
            repository.getNowPlayingRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showNowPlaying(it)
                        view.hideProgressBar()
                    },{
                        view.displayError("Can't refresh channel!")
                    }
                )
        )
    }

    private fun checkIfRefreshNeeded(localMovies: List<MovieResult>) {
        if (localMovies.isEmpty() || repository.isMovieUpToDate(localMovies[0]))
            fetchRemote()
    }

    override fun <T> setView(view: T) {
        this.view = view as NowPlayingContract.NowPlayingView
    }

    override fun dispose() {
        disposable.clear()
    }
}