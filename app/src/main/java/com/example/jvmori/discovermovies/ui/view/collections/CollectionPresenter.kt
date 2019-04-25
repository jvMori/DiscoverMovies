package com.example.jvmori.discovermovies.ui.view.collections

interface CollectionPresenter {
    fun fetchSaved()
    fun setView(view : CollectionView)
}