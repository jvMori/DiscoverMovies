package com.example.jvmori.discovermovies.ui.presenter.collections

import androidx.lifecycle.LifecycleOwner

interface CollectionPresenter {
    fun fetchSaved()
    fun setView(view : CollectionView)
    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner)
}