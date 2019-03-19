package com.example.jvmori.discovermovies.data.network

import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.response.GenreResponse
import com.example.jvmori.discovermovies.data.network.response.MovieDetails
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.Maybe
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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


}