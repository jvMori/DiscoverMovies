package com.example.jvmori.discovermovies.ui.view.details

class DetailsPresenterImpl : DetailsPresenter {

    private lateinit var view: DetailsView

    override fun setView(view: DetailsView) {
       this.view = view
    }

    override fun fetchDetails(id: Int) {
        
    }
}