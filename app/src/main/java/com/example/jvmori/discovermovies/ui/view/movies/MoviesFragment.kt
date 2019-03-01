package com.example.jvmori.discovermovies.ui.view.movies


import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.adapters.MoviesAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movies.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MoviesFragment : Fragment(), MoviesViewInterface {

    private var genreId: Int? = null
    private var moviesPresenter : MoviesPresenter? = null
    private var moviesAdapter : MoviesAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        moviesPresenter = MoviesPresenter(this, MoviesRepository(TmdbAPI.invoke(),
            this.requireContext()))
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genreId = MoviesFragmentArgs.fromBundle(arguments).genre
        genreId?.let {
           //moviesPresenter?.fetchMovies(DiscoverQueryParam(genreId.toString(), 1))
            moviesPresenter?.initMovies(DiscoverQueryParam(genreId.toString(), 1))
            moviesPresenter?.movieList?.observeForever {
                hideProgressBar()
                displayAllItems(it)
            }
        }
    }

    override fun showProgressBar() {
        recyclerViewMovies.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        recyclerViewMovies.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun displayMovie(movieResult: MovieResult) {
        val position = moviesAdapter?.getItemPosition(movieResult)
        position.let {
            moviesAdapter?.setItem(it, movieResult.movieDetails)
            moviesAdapter?.notifyItemChanged(position!!)
        }
    }

    override fun displayAllItems(movieResponse: List<MovieResult>) {
        moviesAdapter = MoviesAdapter(movieResponse.toMutableList())
        recyclerViewMovies!!.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL ,false)
        recyclerViewMovies!!.setHasFixedSize(true)
        recyclerViewMovies!!.adapter = moviesAdapter
    }

    override fun displayError(s: String) {
        Log.i("Data", s)
    }
}
