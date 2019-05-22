package com.example.jvmori.discovermovies.data.network

import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.response.credits.CreditsResponse
import com.example.jvmori.discovermovies.data.network.response.genre.GenreResponse
import com.example.jvmori.discovermovies.data.network.response.movie.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.recommendations.RecommendationsResponse
import com.example.jvmori.discovermovies.data.network.response.search.SearchResponse
import com.example.jvmori.discovermovies.data.network.response.video.VideoResponse
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
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

    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path("movie_id") id: Int) : Observable<CreditsResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendations(@Path("movie_id") id: Int) : Observable<RecommendationsResponse>

    @GET("search/multi")
    fun getSearchedItems(@Query("query") query: String): Single<SearchResponse>

    @GET("trending/movie/{period}")
    fun getTrendingMovies(@Path("period") period: String) : Observable<DiscoverMovieResponse>

    @GET("movie/now_playing")
    fun getNowPlaying() : Flowable<DiscoverMovieResponse>
}