package com.example.jvmori.discovermovies.data.network

import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.response.genre.GenreResponse
import com.example.jvmori.discovermovies.data.network.response.movie.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.video.VideoResponse
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface TmdbAPI
{
    @GET("genre/movie/list")
    fun getGenres() : Maybe<GenreResponse>

    @GET("discover/movie")
    fun getMovies(@QueryMap options : Map<String, String>) : Observable<DiscoverMovieResponse>

    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId") id: Int) : Observable<MovieDetails>

    @GET("movie/{movie_id}/videos")
    fun getVideos(@Path("movie_id") id: Int) : Observable<VideoResponse>
}