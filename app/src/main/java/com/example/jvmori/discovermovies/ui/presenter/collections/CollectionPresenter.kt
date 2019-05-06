package com.example.jvmori.discovermovies.ui.presenter.collections

interface CollectionPresenter {
    fun fetchSaved()
    fun setView(view : CollectionView)
}