package com.mori.jvmori.discovermovies.ui

interface BasePresenter {
    fun <T> setView(view: T)
    fun dispose()
}