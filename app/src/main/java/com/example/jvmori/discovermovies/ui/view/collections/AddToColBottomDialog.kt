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
        createCollectionsList()
        return inflater.inflate(R.layout.add_to_collection_bottom_dialog, container, false)
    }

    private fun createCollectionsList() {
        val list = mutableListOf<String>("Liked", "To Watch", "Watched")
        playlists?.adapter = AddToCollectionAdapter(list)
        playlists?.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
    }
}