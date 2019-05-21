package com.example.jvmori.discovermovies.ui.presenter.nowPlaying

import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NowPlayingPresenterImpl @Inject constructor(
    private val repository: MoviesRepository
) : NowPlayingContract.NowPlayingPresenter {

    private val disposable = CompositeDisposable()
    private lateinit var view: NowPlayingContract.NowPlayingView

    override fun fetchNowPlaying() {

    }

    override fun <T> setView(view: T) {
       this.view = view as NowPlayingContract.NowPlayingView
    }

    override fun dispose() {
        disposable.dispose()
    }
}