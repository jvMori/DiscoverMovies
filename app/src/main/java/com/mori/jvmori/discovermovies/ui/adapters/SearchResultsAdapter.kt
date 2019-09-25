package com.mori.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.IOnClickListener

class SearchResultsAdapter(
    private var onclickListener : IOnClickListener?,
    private var onAddBtnClickListener: MoviesAdapter.OnAddBtnClickListener?,
    private var genres: Map<Int, String>
) : BaseAdapter<MovieResult>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieResult> {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesAdapter.MovieViewHolder(view, onclickListener, onAddBtnClickListener, genres)
    }
}