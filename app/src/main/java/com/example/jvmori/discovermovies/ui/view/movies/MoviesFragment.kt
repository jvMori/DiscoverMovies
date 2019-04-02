package com.example.jvmori.discovermovies.ui.view.movies


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.response.movie.MovieResult
import com.example.jvmori.discovermovies.ui.IOnClickListener
import com.example.jvmori.discovermovies.ui.adapters.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MoviesFragment : Fragment(), MoviesViewInterface, IOnClickListener, MoviesAdapter.IFetchGenres{

    lateinit var genre : Genre
    override fun displayGenres(genres: Genre) {

    }

    private var genreId: Int? = null
    @Inject lateinit var moviesPresenter: MoviesPresenterInterface
    private var moviesAdapter: MoviesAdapter? = null

    override fun onAttach(context: Context) {
        (context.applicationContext as MoviesApplication).movieComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesPresenter.setView(this)
        genreId = MoviesFragmentArgs.fromBundle(arguments).genre
        genreId?.let { genreId ->
            moviesPresenter.let {
                it.parameters = DiscoverQueryParam(genreId.toString(), 1)
                it.moviesDataList.observe(this, Observer { pageList ->
                    pageList?.let {
                        displayAllItems(pageList)
                        moviesAdapter?.submitList(pageList)
                        hideProgressBar()
                    }
                })
            }
        }
        moviesPresenter.moviesDataList.observe(this, Observer {
            moviesAdapter?.submitList(it)
        })
    }

    override fun onMovieItemClicked(movieId: Int) {
       navigateToDetails(movieId)
    }

    private fun navigateToDetails(movieId: Int){
        val action =
            MoviesFragmentDirections.action_moviesFragment_to_detailsFragment().setMovieId(movieId)
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun showProgressBar() {
        recyclerViewMovies.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        recyclerViewMovies.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun setMovieDetails(movieResult: MovieResult) {
        // moviesAdapter?.setItem(movieResult.movieDetails)
        //moviesAdapter?.notifyDataSetChanged()
    }

    override fun displayAllItems(movieResponse: List<MovieResult>) {
        moviesPresenter.let {
            moviesAdapter = MoviesAdapter(this, this)
            recyclerViewMovies!!.layoutManager =
                    LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
            recyclerViewMovies!!.setHasFixedSize(true)
            recyclerViewMovies!!.adapter = moviesAdapter
        }
    }

    override fun fetchGenreById(index: Int, itemId: Int, lastIndex : Int, itemView : View) {
//        moviesPresenter?.fetchGenreById(itemId).subscribe { response ->
//            itemView.categoryItem.append(response.name)
//            if (index != lastIndex)
//                itemView.categoryItem.append(" | ")
//        }
    }

    override fun displayError(s: String) {
        Log.i("Data", s)
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesPresenter.clear()
    }
}
