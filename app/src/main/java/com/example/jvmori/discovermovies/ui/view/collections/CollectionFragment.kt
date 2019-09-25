package com.example.jvmori.discovermovies.ui.view.collections


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.BaseAdapter
import com.example.jvmori.discovermovies.ui.adapters.CollectionAdapter
import com.example.jvmori.discovermovies.ui.adapters.OnMoreBtnClick
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionPresenter
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionView
import com.example.jvmori.discovermovies.util.navigateToCollectionDetails
import com.example.jvmori.discovermovies.util.navigateToDetails
import dagger.android.support.DaggerFragment
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
class CollectionFragment : DaggerFragment(), CollectionView, BaseAdapter.IOnItemClickListener<MovieResult>, OnMoreBtnClick{

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.setView(this)
        presenter.fetchAllCollections()
    }

    override fun displaySaved(movies: List<MovieResult>, collName: String) {
        adapter.mapOfCollections[collName] = movies
        adapter.notifyDataSetChanged()
    }

    override fun displayCollections(collections: List<CollectionData>) {
        adapter = CollectionAdapter(this.requireContext())
        adapter.iOnMovieClicked = this
        adapter.onMoreBtnClick = this
        collections.forEach {
            presenter.fetchSaved(it.collectionName)
        }
        adapter.setItems(collections)
        recyclerViewFavs.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerViewFavs.adapter = adapter
    }

    override fun onMoreBtnClicked(collectionName: String) {
        navigateToCollectionDetails(collectionName, this, R.id.action_collectionFragment_to_collectionDetailsFragment)
    }

    override fun onItemClicked(item: MovieResult) {
        navigateToDetails(item, this, R.id.action_collectionFragment_to_detailsFragment)
    }

    override fun showProgressBar() {

    }

    override fun hideProgressBar() {

    }

    override fun displayError(s: String) {

    }
}
