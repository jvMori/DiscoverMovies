package com.example.jvmori.discovermovies.ui.presenter.collections

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.jvmori.discovermovies.data.local.entity.Collection
import com.example.jvmori.discovermovies.data.repository.collection.CollectionRepository
import javax.inject.Inject

class CollectionPresenterImpl @Inject constructor (
    private var repository: CollectionRepository
): CollectionPresenter {

    private lateinit var collectionView: CollectionView
    private lateinit var lifecycleOwner : LifecycleOwner

    override fun fetchSaved() {
        repository.displayAllSaved(Collection.LIKES.toString()).observe(
            lifecycleOwner, Observer { result ->
                result?.let {
                    collectionView.displaySaved(it)
                }
            }
        )
    }

    override fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun setView(view: CollectionView) {
       collectionView = view
    }
}