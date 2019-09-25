package com.example.jvmori.discovermovies.ui.view.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.AddToCollectionAdapter
import com.example.jvmori.discovermovies.ui.adapters.BaseAdapter
import com.example.jvmori.discovermovies.ui.presenter.collections.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_to_collection_bottom_dialog.*
import javax.inject.Inject

class AddToColBottomDialog(
    private var movieResult : MovieResult,
    private var savingPresenter : SavingBasePresenter?,
    private var collectionPresenter : CollectionPresenter?
) : BottomSheetDialogFragment(),
    BaseAdapter.IOnItemWithIdClickListener<CollectionData>,
    SavingView,
    CollectionView {

    private lateinit var adapter : AddToCollectionAdapter

    override fun displaySavedIcon() {

    }

    override fun displayDeletedIcon() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_to_collection_bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savingPresenter?.setView(this)
        collectionPresenter?.setView(this)
        collectionPresenter?.fetchAllCollections()
        done.setOnClickListener {
            dismiss()
        }
    }

    override fun displayCollections(collections: List<CollectionData>) {
        createCollectionsList(collections)
        savingPresenter?.checkIsInCollection(collections, movieResult)
    }

    override fun displaySaved(movies: List<MovieResult>, collName: String) {

    }

    private fun createCollectionsList(list: List<CollectionData>) {
        adapter = AddToCollectionAdapter()
        adapter.setItems(list)
        adapter.iOnItemWithIdClickListener = this
        playlists?.adapter = adapter
        playlists?.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
    }

    override fun showCheckedIcon(index: Int) {
        adapter.changeData(index, true)
    }

    override fun showUncheckedIcon(index: Int) {
        adapter.changeData(index, false)
    }

    override fun onItemWithIndexClicked(item: CollectionData, index: Int) {
        item.isChecked = !item.isChecked
        adapter.changeData(index, item.isChecked)
        savingPresenter?.saveMovie(movieResult, item.collectionName)
    }

    override fun showProgressBar() {

    }

    override fun hideProgressBar() {

    }

    override fun displayError(s: String) {

    }

}