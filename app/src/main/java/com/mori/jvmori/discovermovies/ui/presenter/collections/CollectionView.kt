package com.mori.jvmori.discovermovies.ui.presenter.collections

import com.mori.jvmori.discovermovies.data.local.entity.CollectionData
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.view.BaseViewInterface

interface CollectionView: BaseViewInterface {
    fun displaySaved(movies: List<MovieResult>, collName : String)
    fun displayCollections(collections : List<CollectionData>)
}