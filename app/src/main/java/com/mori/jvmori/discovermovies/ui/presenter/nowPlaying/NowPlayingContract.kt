package com.mori.jvmori.discovermovies.ui.presenter.nowPlaying

import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.BasePresenter
import com.mori.jvmori.discovermovies.ui.view.BaseViewInterface

interface NowPlayingContract {
    interface NowPlayingPresenter : BasePresenter {
        fun fetchNowPlaying()
    }
    interface NowPlayingView: BaseViewInterface{
        fun showNowPlaying(movies: List<MovieResult>)
    }
}