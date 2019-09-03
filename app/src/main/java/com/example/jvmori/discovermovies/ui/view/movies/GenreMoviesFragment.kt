package com.example.jvmori.discovermovies.ui.view.movies

import android.content.Context
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import javax.inject.Inject
import javax.inject.Named

class GenreMoviesFragment : MoviesFragment() {

    @field:[Inject Named("Movies")]
    override lateinit var moviesPresenter: MoviesPresenterInterface

}