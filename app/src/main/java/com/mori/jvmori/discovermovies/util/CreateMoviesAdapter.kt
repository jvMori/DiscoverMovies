package com.mori.jvmori.discovermovies.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mori.jvmori.discovermovies.ui.IOnClickListener
import com.mori.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.mori.jvmori.discovermovies.ui.view.search.SearchFragment


fun createAndSetupAdapter(
    onClickListener: IOnClickListener,
    recyclerViewMovies: RecyclerView?,
    context: Context,
    onAddBtnClickListener: MoviesAdapter.OnAddBtnClickListener
): MoviesAdapter {
    val moviesAdapter = MoviesAdapter(onClickListener)
    moviesAdapter?.setGenres(SearchFragment.genresMap)
    recyclerViewMovies!!.layoutManager =
        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    recyclerViewMovies!!.setHasFixedSize(true)
    recyclerViewMovies!!.adapter = moviesAdapter
    moviesAdapter.setOnAddBtnClickListener(onAddBtnClickListener)
    return moviesAdapter
}