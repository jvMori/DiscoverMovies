package com.example.jvmori.discovermovies.ui.presenter.collections


interface CollectionPresenter {
    fun fetchSaved(collectionName : String)
    fun setView(view : CollectionView)
}