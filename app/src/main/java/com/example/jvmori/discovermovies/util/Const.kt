package com.example.jvmori.discovermovies.util

class Const
{
    companion object {
        const val API_KEY = "6a78422b468a1d74ae224a5747a35666"
        const val MOVIE = "movie"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val base_poster_url = "https://image.tmdb.org/t/p/w185/"
        const val STALE_MS = 3600 * 1000 // data is stale after 1 hour
        const val base_backdrop_url = "https://image.tmdb.org/t/p/w500/"
        const val genreIdForTrendingMovies = 1000
    }
}