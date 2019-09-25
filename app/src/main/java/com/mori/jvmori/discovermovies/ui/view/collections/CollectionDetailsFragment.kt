package com.mori.jvmori.discovermovies.ui.view.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.local.entity.CollectionData
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.IOnClickListener
import com.mori.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.mori.jvmori.discovermovies.ui.adapters.SearchResultsAdapter
import com.mori.jvmori.discovermovies.ui.presenter.collections.CollectionPresenter
import com.mori.jvmori.discovermovies.ui.presenter.collections.CollectionView
import com.mori.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenter
import com.mori.jvmori.discovermovies.ui.view.search.SearchFragment
import com.mori.jvmori.discovermovies.util.collectionDetailsKey
import com.mori.jvmori.discovermovies.util.navigateToDetails
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class CollectionDetailsFragment : DaggerFragment(), CollectionView, IOnClickListener, MoviesAdapter.OnAddBtnClickListener{

    private var collectionName : String? = null
    @Inject
    lateinit var presenter: CollectionPresenter
    @Inject
    lateinit var savingPresenter : SavingBasePresenter
    @Inject
    lateinit var collectionPresenter : CollectionPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        collectionName = arguments?.getString(collectionDetailsKey)
        collectionName?.let{
            presenter.fetchSaved(it)
        }
    }

    override fun displaySaved(movies: List<MovieResult>, collName: String) {
        val adapter = SearchResultsAdapter(this, this, SearchFragment.genresMap)
        adapter.setItems(movies)
        recyclerViewMovies.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerViewMovies.adapter = adapter
    }

    override fun onMovieClicked(movieResult: MovieResult) {
        navigateToDetails(movieResult, this, R.id.action_collectionDetailsFragment_to_detailsFragment)
    }

    override fun onAddClicked(movieResult: MovieResult) {
        val bottomSheetDialogFragment = AddToColBottomDialog(movieResult, savingPresenter, collectionPresenter)
        bottomSheetDialogFragment.show(this.requireFragmentManager(), "Bottom Sheet Dialog")
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        recyclerViewMovies.visibility = View.GONE
    }
    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
        recyclerViewMovies.visibility = View.VISIBLE
    }

    override fun displayError(s: String) {

    }
    override fun displayCollections(collections: List<CollectionData>) {

    }

}