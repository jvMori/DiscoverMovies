package com.mori.jvmori.discovermovies.ui.presenter.details

import com.mori.jvmori.discovermovies.data.network.response.credits.Cast
import com.mori.jvmori.discovermovies.data.network.response.credits.Crew
import com.mori.jvmori.discovermovies.data.network.response.movie.MovieDetails
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.data.network.response.video.VideoResponse
import com.mori.jvmori.discovermovies.ui.view.BaseViewInterface

interface DetailsView  : BaseViewInterface{
    fun showResults(movieDetails: MovieDetails)
    fun getVideo(video : VideoResponse)
    fun showCast(cast: List<Cast>)
    fun showCrew(crew: List<Crew>)
    fun showRecommendations(recommendations : List<MovieResult>)
}