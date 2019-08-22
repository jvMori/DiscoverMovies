package com.example.jvmori.discovermovies.ui.presenter.collections

import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.BasePresenter

interface SavingBasePresenter : BasePresenter{
    fun saveMovie(movieResult: MovieResult, collection : String)
    fun updateFavIcon(movieResult: MovieResult)
    fun checkIsInCollection(items : List<CollectionData>, movieResult: MovieResult)
}