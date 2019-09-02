package com.example.jvmori.discovermovies.ui.view.movies

import android.content.Context
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import javax.inject.Inject
import javax.inject.Named

class TrendingMoviesFragment : MoviesFragment() {

    @field:[Inject Named("TrendingMovies")]
    override lateinit var moviesPresenter: MoviesPresenterInterface

    override fun onAttach(context: Context) {
        (context.applicationContext as MoviesApplication).movieComponent.inject(this)
        super.onAttach(context)
    }
}