package com.example.jvmori.discovermovies.ui.view.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.ui.adapters.AddToCollectionAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_to_collection_bottom_dialog.*

class AddToColBottomDialog : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_to_collection_bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createCollectionsList()
    }

    private fun createCollectionsList() {
        val list = mutableListOf<String>("Favorites", "To Watch", "Watched")
        val adapter = AddToCollectionAdapter()
        adapter.setItems(list)
        playlists?.adapter = adapter
        playlists?.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
    }
}