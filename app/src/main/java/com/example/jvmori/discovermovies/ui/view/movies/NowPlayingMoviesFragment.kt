package com.example.jvmori.discovermovies.ui.view.movies

import androidx.fragment.app.Fragment
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.example.jvmori.discovermovies.util.Const
import javax.inject.Inject
import javax.inject.Named
import com.example.jvmori.discovermovies.util.navigateToDetails

class NowPlayingMoviesFragment : MoviesFragment() {

    @field:[Inject Named(Const.nowPlayingNamed)]
    override lateinit var moviesPresenter: MoviesPresenterInterface

    override fun navigateToDetails(movieResult: MovieResult, fragment: Fragment) {
        navigateToDetails(
            movieResult,
            this,
            R.id.action_nowPlayingMoviesFragment_to_detailsFragment
        )
    }
}