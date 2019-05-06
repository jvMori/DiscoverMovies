package com.example.jvmori.discovermovies.ui.presenter.collections

import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import javax.inject.Inject

class CollectionPresenterImpl @Inject constructor (
    private var repository: MoviesRepository
): CollectionPresenter {

    private lateinit var collectionView: CollectionView

    override fun fetchSaved() {

    }

    override fun setView(view: CollectionView) {
       collectionView = view
    }
}