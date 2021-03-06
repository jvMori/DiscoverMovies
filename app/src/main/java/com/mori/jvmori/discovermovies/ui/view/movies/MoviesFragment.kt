package com.mori.jvmori.discovermovies.ui.view.movies


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.IOnClickListener
import com.mori.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.mori.jvmori.discovermovies.ui.presenter.collections.CollectionPresenter
import com.mori.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenter
import com.mori.jvmori.discovermovies.ui.presenter.movies.MoviesPresenterInterface
import com.mori.jvmori.discovermovies.ui.presenter.movies.MoviesViewInterface
import com.mori.jvmori.discovermovies.ui.view.collections.AddToColBottomDialog
import com.mori.jvmori.discovermovies.util.createAndSetupAdapter
import com.mori.jvmori.discovermovies.util.genreIdKey
import dagger.android.support.DaggerFragment
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
abstract class MoviesFragment : DaggerFragment(),
    MoviesViewInterface,
    IOnClickListener,
    MoviesAdapter.OnAddBtnClickListener {

    private var genreId: Int? = null

    abstract var moviesPresenter: MoviesPresenterInterface

    @Inject
    lateinit var savingPresenter: SavingBasePresenter
    @Inject
    lateinit var collectionPresenter: CollectionPresenter
    private var moviesAdapter: MoviesAdapter? = null

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

        genreId = arguments?.getInt(genreIdKey)
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
        val bottomSheetDialogFragment = AddToColBottomDialog(movieResult, savingPresenter, collectionPresenter)
        bottomSheetDialogFragment.show(this.requireFragmentManager(), "Bottom Sheet Dialog")
    }

    override fun onMovieClicked(movieResult: MovieResult) {
        navigateToDetails(movieResult, this)
    }

    abstract fun navigateToDetails(movieResult: MovieResult, fragment: Fragment)

    override fun showProgressBar() {
        recyclerViewMovies.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        recyclerViewMovies.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun displayAllItems(movieResponse: List<MovieResult>) {
        moviesAdapter = createAndSetupAdapter(this, recyclerViewMovies, this.requireContext(), this)
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
