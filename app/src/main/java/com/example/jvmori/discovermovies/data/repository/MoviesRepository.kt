package com.example.jvmori.discovermovies.data.repository

import android.content.Context
import com.example.jvmori.discovermovies.data.local.database.MovieDatabase
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.response.GenreResponse
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.Observable
import io.reactivex.Single


class MoviesRepository(
    private val tmdpApi: TmdbAPI,
    context: Context
) {
    private val genreDao = MovieDatabase.invoke(context).genreDao()

    fun getMoviesToDiscover(
        queryParam: DiscoverQueryParam
    ): Observable<DiscoverMovieResponse> {
        val parameters: HashMap<String, String> = HashMap()
        parameters["sort_by"] = "popularity.desc"
        parameters["page"] = queryParam.page.toString()
        parameters["vote_average.gte"] = queryParam.vote.toString()
        parameters["with_genres"] = queryParam.genresId
        parameters["year"] = queryParam.year

        return tmdpApi.getMovies(parameters)
    }



    fun getAllGenres() : Observable<GenreResponse>{
        return tmdpApi.getGenres()
    }
    fun getGenreById(genreId: Int): Single<Genre> {
        return genreDao.getGenre(genreId)
    }
}