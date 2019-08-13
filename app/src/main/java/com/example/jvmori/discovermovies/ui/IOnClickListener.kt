package com.example.jvmori.discovermovies.ui

import com.example.jvmori.discovermovies.data.local.entity.MovieResult

public interface IOnClickListener{
    fun onMovieItemClicked(movieId : Int)
    fun onMovieClicked(movieResult: MovieResult)
}