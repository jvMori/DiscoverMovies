package com.mori.jvmori.discovermovies.ui.presenter.details

import com.mori.jvmori.discovermovies.ui.BasePresenter

interface DetailsPresenter : BasePresenter{
    fun getDetails(id : Int)
}