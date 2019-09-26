package com.mori.jvmori.discovermovies.ui.presenter.nowPlaying

import com.mori.jvmori.discovermovies.data.repository.movies.BaseMoviesRepository
import com.mori.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import com.mori.jvmori.discovermovies.util.Const
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingPresenterImpl @Inject constructor(
    private val repository: BaseMoviesRepository
) : NowPlayingContract.NowPlayingPresenter {

    private val disposable = CompositeDisposable()
    private lateinit var view: NowPlayingContract.NowPlayingView

    override fun fetchNowPlaying() {
        disposable.add(
            repository
                .getMovies(DiscoverQueryParam(genresId = Const.genreIdForNowPlayingMovies.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.hideProgressBar()
                        view.showNowPlaying(it.results)
                    },
                    {
                        view.hideProgressBar()
                        view.displayError("Error while loading now playing movies")
                    }
                )

        )
    }

    override fun <T> setView(view: T) {
        this.view = view as NowPlayingContract.NowPlayingView
    }

    override fun dispose() {
        disposable.clear()
    }
}