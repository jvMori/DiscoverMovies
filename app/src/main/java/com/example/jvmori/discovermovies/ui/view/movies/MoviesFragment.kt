package com.example.jvmori.discovermovies.ui.view.movies


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.IOnClickListener
import com.example.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.example.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenter
import com.example.jvmori.discovermovies.ui.presenter.collections.SavingView
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.example.jvmori.discovermovies.ui.presenter.movies.MoviesViewInterface
import com.example.jvmori.discovermovies.ui.view.collections.AddToColBottomDialog
import com.example.jvmori.discovermovies.ui.view.search.SearchFragment
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
class MoviesFragment : Fragment(),
    MoviesViewInterface,
    SavingView,
    IOnClickListener,
    MoviesAdapter.OnAddBtnClickListener,
    AddToColBottomDialog.IOnCollectionItemClicked {

    private var genreId: Int? = null
    @Inject
    lateinit var moviesPresenter: MoviesPresenterInterface
    @Inject
    lateinit var savingPresenter: SavingBasePresenter
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
        savingPresenter.setView(this)
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

    override fun onAddClicked(movieResult: MovieResult) {
        val bottomSheetDialogFragment = AddToColBottomDialog(movieResult)
        bottomSheetDialogFragment.iOnAddToCollectionListner = this
        bottomSheetDialogFragment.show(this.requireFragmentManager(), "Bottom Sheet Dialog")
    }

    override fun onMovieItemClicked(movieId: Int) {
        navigateToDetails(movieId)
    }

    override fun displayDeletedIcon() {
        Toast.makeText(this.requireContext(), "Deleted!", Toast.LENGTH_SHORT).show()
    }

    override fun displaySavedIcon() {
        //TODO: show snackbar instead of toast
        Toast.makeText(this.requireContext(), "Saved!", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToDetails(movieId: Int) {
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

    override fun displayAllItems(movieResponse: List<MovieResult>) {
        moviesPresenter.let {
            moviesAdapter = MoviesAdapter(this)
            moviesAdapter?.setGenres(SearchFragment.genresMap)
            recyclerViewMovies!!.layoutManager =
                LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
            recyclerViewMovies!!.setHasFixedSize(true)
            recyclerViewMovies!!.adapter = moviesAdapter
        }
        moviesAdapter?.setOnAddBtnClickListener(this)
    }

    override fun onAddToCollection(nameCollection: String, movieResult: MovieResult) {
        savingPresenter.saveMovie(movieResult, nameCollection)
    }

    override fun displayError(s: String) {
        Log.i("Data", s)
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesPresenter.clear()
        savingPresenter.dispose()
    }
}
