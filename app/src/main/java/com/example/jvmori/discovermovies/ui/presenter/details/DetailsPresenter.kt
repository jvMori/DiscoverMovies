package com.example.jvmori.discovermovies.ui.presenter.details

import com.example.jvmori.discovermovies.ui.BasePresenter

interface DetailsPresenter : BasePresenter{
    fun getDetails(id : Int)
}