package com.example.jvmori.discovermovies.ui.view.search

import androidx.appcompat.widget.SearchView

interface SearchPresenter
{
    fun setView(view : SearchViewInterface)
    fun searchItems()
    fun onSearchViewQueryChanged(searchView: SearchView)
    fun clear()
}