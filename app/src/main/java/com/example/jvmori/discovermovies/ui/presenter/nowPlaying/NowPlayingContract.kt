package com.example.jvmori.discovermovies.ui.presenter.nowPlaying

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.BasePresenter
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface NowPlayingContract {
    interface NowPlayingPresenter : BasePresenter {
        fun fetchNowPlaying()
    }
    interface NowPlayingView: BaseViewInterface{
        fun showNowPlaying(movies: List<MovieResult>)
    }
}