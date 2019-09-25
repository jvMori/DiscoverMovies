package com.mori.jvmori.discovermovies.ui.view.movies

import androidx.fragment.app.Fragment
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.mori.jvmori.discovermovies.util.Const
import javax.inject.Inject
import javax.inject.Named
import com.mori.jvmori.discovermovies.util.navigateToDetails

class TrendingMoviesFragment : MoviesFragment() {

    @field:[Inject Named(Const.trendingNamed)]
    override lateinit var moviesPresenter: MoviesPresenterInterface

    override fun navigateToDetails(movieResult: MovieResult, fragment: Fragment) {
        navigateToDetails(movieResult, this, R.id.action_trendingMoviesFragment_to_detailsFragment)
    }
}