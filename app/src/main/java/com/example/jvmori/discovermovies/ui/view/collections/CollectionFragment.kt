package com.example.jvmori.discovermovies.ui.view.collections


import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.CollectionAdapter
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionPresenter
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionView
import kotlinx.android.synthetic.main.fragment_collection.*
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

    @Inject
    lateinit var presenter: CollectionPresenter
    private lateinit var adapter: CollectionAdapter

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
        presenter.fetchAllCollections()
    }

    override fun displaySaved(movies: List<MovieResult>, collName: String) {
        adapter.mapOfCollections[collName] = movies
        adapter.notifyDataSetChanged()
        adapter.viewHolder.update(movies)
    }

    override fun displayCollections(collections: List<CollectionData>) {
        val list = mutableListOf<String>("Favorites", "To Watch", "Watched")
        val col = mutableListOf(CollectionData("Favorites",0), CollectionData("To Watch",0), CollectionData("Watched",0))
        adapter = CollectionAdapter(this.requireContext())
        list.forEach {
            presenter.fetchSaved(it)
        }
        adapter.setItems(col)
        recyclerViewFavs.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerViewFavs.adapter = adapter
    }
}
