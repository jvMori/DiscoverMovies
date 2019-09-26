package com.example.jvmori.discovermovies.ui

interface BasePresenter {
    fun <T> setView(view: T)
    fun dispose()
}