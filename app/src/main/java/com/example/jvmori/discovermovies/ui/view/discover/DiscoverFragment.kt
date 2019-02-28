package com.example.jvmori.discovermovies.ui.view.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.GenreResponse
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.adapters.GenreAdapter
import kotlinx.android.synthetic.main.fragment_discover.*

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
        val genrePresenter = GenresPresenter(this, MoviesRepository(
            TmdbAPI.invoke(), this.requireContext()
        ))
        genrePresenter.getGenres()
    }

    override fun showProgressBar() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_circular.visibility = View.GONE
    }

    override fun displayGenres(genreResponse: List<Genre>) {
        Log.i("result", genreResponse.toString())
        createAdapter(genreResponse)
    }

    override fun displayError(s: String) {
        Log.i("result", s)
    }

    private fun createAdapter(genres : List<Genre>){
        val adapter = GenreAdapter(genres, this)
        genresRv.layoutManager = GridLayoutManager(this.context, 2, RecyclerView.VERTICAL, false)
        genresRv.setHasFixedSize(true)
        genresRv.adapter = adapter
    }
}
