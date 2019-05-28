package com.example.jvmori.discovermovies.data.repository.details

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.response.credits.Cast
import com.example.jvmori.discovermovies.data.network.response.credits.Crew
import com.example.jvmori.discovermovies.data.network.response.movie.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.video.VideoResponse
import io.reactivex.Observable
import io.reactivex.Single

interface DetailsRepository {
    fun getDetails(id: Int): Observable<MovieDetails>
    fun getVideos(id: Int): Observable<VideoResponse>
    fun setCreditsConnectable(movieId: Int)
    fun connectToCredits()
    fun getCast(): Single<List<Cast>>
    fun getCrew(): Single<List<Crew>>
    fun getRecommendations(movieId: Int): Observable<List<MovieResult>>
}