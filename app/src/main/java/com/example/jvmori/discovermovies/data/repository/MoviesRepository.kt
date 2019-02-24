package com.example.jvmori.discovermovies.data.repository

import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse
import io.reactivex.Observable

class MoviesRepository(
    private val tmdpApi: TmdbAPI
) {
    fun getMoviesToDiscover(
        page: Int,
        vote: Int = 5,
        genresId: String = "",
        year : String = ""
    ): Observable<DiscoverMovieResponse> {
        val parameters: HashMap<String, String> = HashMap()
        parameters["sort_by"] = "popularity.desc"
        parameters["page"] = page.toString()
        parameters["vote_average.gte"] = vote.toString()
        parameters["with_genres"] = genresId
        parameters["year"] = year

        return tmdpApi.getMovies(parameters)
    }
}