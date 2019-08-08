package com.example.jvmori.discovermovies.ui.presenter.collections

import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult

interface CollectionView {
    fun displaySaved(movies: List<MovieResult>, collName : String)
    fun displayCollections(collections : List<CollectionData>)
}