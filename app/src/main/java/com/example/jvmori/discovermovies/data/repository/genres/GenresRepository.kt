package com.example.jvmori.discovermovies.data.repository.genres

import com.example.jvmori.discovermovies.data.local.entity.Genre
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface GenresRepository {
    fun getAllGenresLocal(): Maybe<List<Genre>>
    fun getGenres(): Observable<List<Genre>>
    fun getGenreById(genreId: Int): Single<Genre>
}