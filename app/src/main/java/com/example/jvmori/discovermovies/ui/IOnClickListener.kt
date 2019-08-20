package com.example.jvmori.discovermovies.ui

import com.example.jvmori.discovermovies.data.local.entity.MovieResult

public interface IOnClickListener{
    fun onMovieClicked(movieResult: MovieResult)
}