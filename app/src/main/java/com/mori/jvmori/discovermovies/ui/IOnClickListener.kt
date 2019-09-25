package com.mori.jvmori.discovermovies.ui

import com.mori.jvmori.discovermovies.data.local.entity.MovieResult

public interface IOnClickListener{
    fun onMovieClicked(movieResult: MovieResult)
}