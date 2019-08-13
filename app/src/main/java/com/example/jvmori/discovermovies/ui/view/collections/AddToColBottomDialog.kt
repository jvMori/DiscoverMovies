package com.example.jvmori.discovermovies.ui.view.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.AddToCollectionAdapter
import com.example.jvmori.discovermovies.ui.adapters.BaseAdapter
import com.example.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenter
import com.example.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenterImpl
import com.example.jvmori.discovermovies.ui.presenter.collections.SavingView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_to_collection_bottom_dialog.*
import javax.inject.Inject

class AddToColBottomDialog(
    private var movieResult : MovieResult
) : BottomSheetDialogFragment(), BaseAdapter.IOnItemClickListener<String>, SavingView{

    override fun displaySavedIcon() {

    }

    override fun displayDeletedIcon() {

    }

    var iOnAddToCollectionListner : IOnCollectionItemClicked? = null
    lateinit var savingPresenter : SavingBasePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_to_collection_bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savingPresenter.setView(this)
        createCollectionsList()
    }

    private fun createCollectionsList() {
        //TODO: fetch data to list dynamically
        val list = mutableListOf<String>("Favorites", "To Watch", "Watched")
        val adapter = AddToCollectionAdapter()
        adapter.setItems(list)
        adapter.iOnItemClickListener = this
        playlists?.adapter = adapter
        playlists?.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
    }

    override fun onItemClicked(item: String) {
        //TODO: toggle checkbox

        iOnAddToCollectionListner?.onAddToCollection(item, movieResult)
       // savingPresenter.saveMovie(movieResult, item)
    }

    interface IOnCollectionItemClicked {
        fun onAddToCollection(nameCollection: String, movieResult: MovieResult)
    }
}