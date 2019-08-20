package com.example.jvmori.discovermovies.ui.presenter.collections

import android.util.Log
import com.example.jvmori.discovermovies.MainActivity
import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import com.example.jvmori.discovermovies.data.repository.collection.CollectionRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CollectionPresenterImpl @Inject constructor(
    private var repository: CollectionRepository
) : CollectionPresenter {

    private lateinit var collectionView: CollectionView
    private val disposable = CompositeDisposable()

    override fun fetchAllCollections() {
        disposable.add(
            repository.getAllCollectionsNames()
                .subscribe(
                    { success ->
                        collectionView.displayCollections(success)
                    }, {
                        Log.i(MainActivity.TAG, "Something went wrong!")
                    }
                )
        )
    }

    override fun fetchSaved(collectionName: String) {
        disposable.add(
            repository.displayAllSaved(CollectionData(collectionName, 0, false)).subscribe(
                { success ->
                    collectionView.displaySaved(success, collectionName)
                },
                {
                    Log.i(MainActivity.TAG, "Something went wrong!")
                }
            )
        )
    }


    override fun setView(view: CollectionView) {
        collectionView = view
    }
}