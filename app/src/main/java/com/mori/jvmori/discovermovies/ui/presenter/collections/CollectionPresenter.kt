package com.mori.jvmori.discovermovies.ui.presenter.collections


interface CollectionPresenter {
    fun setView(view : CollectionView)
    fun fetchSaved(collectionName: String)
    fun fetchAllCollections()
}