package com.example.jvmori.discovermovies.ui.view.collections


import android.content.Context
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.MainActivity

import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.data.local.entity.Collection
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.example.jvmori.discovermovies.ui.adapters.SearchResultsAdapter
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionPresenter
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionView
import com.example.jvmori.discovermovies.ui.view.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_collection.*
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
class CollectionFragment : Fragment(), CollectionView {

    @Inject lateinit var presenter : CollectionPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as MoviesApplication).movieComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.setView(this)
        presenter.fetchSaved(Collection.LIKES.toString())
    }

    override fun displaySaved(movies : List<MovieResult>) {
        Log.i(MainActivity.TAG, movies.toString())
        val adapter = SearchResultsAdapter(null, null, SearchFragment.genresMap)
        adapter.setItems(movies)
        recyclerViewFavs!!.layoutManager =
                LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerViewFavs!!.setHasFixedSize(true)
        recyclerViewFavs.adapter = adapter
    }
}
