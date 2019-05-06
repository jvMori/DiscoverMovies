package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.IOnClickListener

class SearchResultsAdapter(
    private var onclickListener : IOnClickListener?,
    private var onFavIconClickListener: MoviesAdapter.OnFavIconClickListener?,
    private var genres: Map<Int, String>
) : BaseAdapter<MovieResult>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieResult> {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesAdapter.MovieViewHolder(view, onclickListener, onFavIconClickListener, genres)
    }
}