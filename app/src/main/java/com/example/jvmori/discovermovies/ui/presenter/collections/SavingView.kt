package com.example.jvmori.discovermovies.ui.presenter.collections

interface SavingView {
    fun displaySavedIcon()
    fun displayDeletedIcon()
    fun showCheckedIcon(index : Int)
    fun showUncheckedIcon(index : Int)
}