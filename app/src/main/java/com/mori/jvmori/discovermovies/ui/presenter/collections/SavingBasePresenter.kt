package com.mori.jvmori.discovermovies.ui.presenter.collections

import com.mori.jvmori.discovermovies.data.local.entity.CollectionData
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.ui.BasePresenter

interface SavingBasePresenter : BasePresenter{
    fun saveMovie(movieResult: MovieResult, collection : String)
    fun updateFavIcon(movieResult: MovieResult)
    fun checkIsInCollection(items : List<CollectionData>, movieResult: MovieResult)
}