package com.example.jvmori.discovermovies.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.network.response.GenreResponse

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DiscoverFragment : Fragment(), GenresViewInterface {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genrePresenter = GenresPresenter(this)
        genrePresenter.getMovies()
    }

    override fun showProgressBar() {

    }

    override fun hideProgressBar() {

    }

    override fun displayGenres(genreResponse: GenreResponse) {
        Log.i("result", genreResponse.genres.toString())
    }

    override fun displayError(s: String) {
        Log.i("result", s)
    }
}
