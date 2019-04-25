package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.MovieResult

class SearchResultsAdapter : BaseAdapter<MovieResult>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieResult> {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesAdapter.MovieViewHolder(view)
    }
}