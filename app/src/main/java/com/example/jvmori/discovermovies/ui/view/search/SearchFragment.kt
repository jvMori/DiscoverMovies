package com.example.jvmori.discovermovies.ui.view.search


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.data.network.response.movie.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.SearchResultsAdapter
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment(), SearchViewInterface {

    @Inject
    lateinit var searchPresenter: SearchPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MoviesApplication).movieComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchPresenter.setView(this)
        searchPresenter.onSearchViewQueryChanged(searchView)
        searchPresenter.searchItems()
        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                searchView.clearFocus()
                searchView.setIconifiedByDefault(true)
                noResultsLayout.visibility = View.VISIBLE
                searchResults.visibility = View.GONE
                progressSearch.visibility = View.GONE
                return false
            }
        })
        progressSearch.visibility = View.GONE
        handleAppBarCollapsing()
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
    }

    override fun displayResults(results: List<MovieResult>) {
        noResultsLayout.visibility = View.GONE
        searchResults.visibility = View.VISIBLE
        val adapter = SearchResultsAdapter()
        adapter.setItems(results)
        searchResults.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        searchResults.adapter = adapter
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

