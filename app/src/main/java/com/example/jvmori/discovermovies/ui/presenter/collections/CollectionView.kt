package com.example.jvmori.discovermovies.ui.presenter.collections

import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.view.BaseViewInterface

interface CollectionView: BaseViewInterface {
    fun displaySaved(movies: List<MovieResult>, collName : String)
    fun displayCollections(collections : List<CollectionData>)
}