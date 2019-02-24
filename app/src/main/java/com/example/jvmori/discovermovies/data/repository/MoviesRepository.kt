package com.example.jvmori.discovermovies.data.repository

import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.Observable

class MoviesRepository(
    private val tmdpApi: TmdbAPI
) {
    fun getMoviesToDiscover(
        queryParam : DiscoverQueryParam
    ): Observable<DiscoverMovieResponse> {
        val parameters: HashMap<String, String> = HashMap()
        parameters["sort_by"] = "popularity.desc"
        parameters["page"] = queryParam.page.toString()
        parameters["vote_average.gte"] = queryParam.vote.toString()
        parameters["with_genres"] = queryParam.genresId
        parameters["year"] = queryParam.year

        return tmdpApi.getMovies(parameters)
    }
}