package com.example.jvmori.discovermovies.ui.view.search

import androidx.appcompat.widget.SearchView

interface SearchPresenter
{
    fun searchItems(query: String)
    fun searchViewQueryChanged(searchView: SearchView)
    fun clear()
}