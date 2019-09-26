package com.example.jvmori.discovermovies.ui.presenter.search

import androidx.appcompat.widget.SearchView

interface SearchPresenter
{
    fun setView(view : SearchViewInterface)
    fun searchItems()
    fun onSearchViewQueryChanged(searchView: SearchView)
    fun clear()
}