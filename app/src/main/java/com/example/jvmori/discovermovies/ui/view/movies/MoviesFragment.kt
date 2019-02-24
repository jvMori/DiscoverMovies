package com.example.jvmori.discovermovies.ui.view.movies


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.repository.MoviesRepository


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MoviesFragment : Fragment(), MoviesViewInterface{
    private var genreId : Int? = null
    private var moviesPresenter : MoviesPresenter = MoviesPresenter(this, MoviesRepository(TmdbAPI.invoke(), this.context!!))
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genreId =  MoviesFragmentArgs.fromBundle(arguments).genre
        genreId?.let {
            moviesPresenter.getMovies(DiscoverQueryParam(it.toString(), 1))
        }
    }
    override fun showProgressBar() {

    }

    override fun hideProgressBar() {

    }

    override fun displayGenres(movieResponse: DiscoverMovieResponse) {
        Log.i("Data", movieResponse.results.toString())

    }

    override fun displayError(s: String) {
        Log.i("Data", s)
    }
}
