package com.mori.jvmori.discovermovies.ui.view.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.local.entity.Genre
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.IOnClickListener
import com.mori.jvmori.discovermovies.ui.adapters.GenreAdapter
import com.mori.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.mori.jvmori.discovermovies.ui.adapters.SearchResultsAdapter
import com.mori.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenter
import com.mori.jvmori.discovermovies.ui.presenter.collections.SavingView
import com.mori.jvmori.discovermovies.ui.presenter.genres.GenresPresenterInterface
import com.mori.jvmori.discovermovies.ui.presenter.genres.GenresViewInterface
import com.mori.jvmori.discovermovies.ui.presenter.search.SearchPresenter
import com.mori.jvmori.discovermovies.ui.presenter.search.SearchViewInterface
import kotlinx.android.synthetic.main.fragment_search.*
import com.mori.jvmori.discovermovies.util.navigateToDetails
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.DaggerFragment
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : DaggerFragment(), SearchViewInterface,
    GenresViewInterface,
    SavingView,
    GenreAdapter.IOnGenreClick,
    IOnClickListener,
    MoviesAdapter.OnAddBtnClickListener {

    override fun showCheckedIcon(index: Int) {

    }

    override fun showUncheckedIcon(index: Int) {

    }


    @Inject
    lateinit var searchPresenter: SearchPresenter
    @Inject
    lateinit var genrePresenter: GenresPresenterInterface
    @Inject lateinit var savingPresenter: SavingBasePresenter

    companion object {
        var genresMap = mutableMapOf<Int, String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        genrePresenter.setView(this)
        savingPresenter.setView(this)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressSearch.visibility = View.GONE
        searchPresenter.setView(this)
        searchPresenter.onSearchViewQueryChanged(searchView)
        searchPresenter.searchItems()
        searchView.setOnCloseListener {
            searchView.clearFocus()
            searchView.setIconifiedByDefault(true)
            noResultsLayout.visibility = View.VISIBLE
            searchResults.visibility = View.GONE
            progressSearch.visibility = View.GONE
            false
        }
        progressSearch.visibility = View.GONE
        handleAppBarCollapsing()
        genrePresenter.getGenres()
    }

    private fun handleAppBarCollapsing() {
        appBarSearch.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                searchView.setBackgroundResource(R.color.transparent)
            } else {
                searchView.setBackgroundResource(R.drawable.rectangle)
            }
        })
    }

    override fun onQuerySubmit() {
        searchView.setQuery("", false)
        searchView.setIconifiedByDefault(true)
        searchView.clearFocus()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.clear()
        savingPresenter.dispose()
    }

    override fun displayResults(results: List<MovieResult>) {
        noResultsLayout.visibility = View.GONE
        searchResults.visibility = View.VISIBLE
        val adapter = SearchResultsAdapter(this, this, genresMap)
        adapter.setItems(results)
        searchResults.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        searchResults.adapter = adapter
    }

    override fun onMovieClicked(movieResult: MovieResult) {
        navigateToDetails(movieResult, this, R.id.specifyMovieId)
    }

    override fun displayGenres(genreResponse: List<Genre>) {
        createAdapter(genreResponse)
        genreResponse.forEach {
            genresMap[it.idGenre] = it.name
        }
    }

    private fun createAdapter(genres: List<Genre>) {
        val adapter = GenreAdapter(this)
        adapter.setItems(genres)
        genresRv?.layoutManager = GridLayoutManager(this.context, 2, RecyclerView.VERTICAL, false)
        genresRv?.setHasFixedSize(true)
        genresRv?.adapter = adapter
    }

    override fun onGenreClicked(item: Genre) {
        com.mori.jvmori.discovermovies.util.navigateToMovieList(item, this, R.id.specifyGenre)
    }

    override fun displayDeletedIcon() {

    }

    override fun displaySavedIcon() {

    }

    override fun onAddClicked(movieResult: MovieResult) {
        //savingPresenter.saveMovie(movieResult)
    }

    override fun displayError(s: String) {

    }

    override fun showProgressBar() {
        progressSearch.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressSearch.visibility = View.GONE
    }
}

