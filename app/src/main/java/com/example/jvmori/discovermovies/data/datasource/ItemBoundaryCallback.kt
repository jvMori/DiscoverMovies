package com.example.jvmori.discovermovies.data.datasource

import androidx.paging.PagedList
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.MovieResult

class ItemBoundaryCallback(
    private val service: TmdbAPI,
    private val database: MovieDatabase
) : PagedList.BoundaryCallback<MovieResult>() {


    override fun onItemAtEndLoaded(itemAtEnd: MovieResult) {
        super.onItemAtEndLoaded(itemAtEnd)
    }
}