package com.example.jvmori.discovermovies.data.local

import android.graphics.Movie
import androidx.room.*
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface SavedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieResult)

    @Delete
    fun delete(movie: MovieResult)

    @Query("Select * from saved_movies")
    fun getAllSaved(): Observable<List<MovieResult>>

    @Query("Select * from saved_movies where id like :movieId")
    fun getMovie(movieId: Int): Single<MovieResult>

    @Query("Select * from saved_movies where isTrending AND period like:periodTime")
    fun getAllTrending(periodTime: String): Single<List<MovieResult>>

    @Query("Delete from saved_movies where isTrending AND period like:periodTime")
    fun deleteTrending(periodTime: String)

    @Transaction
    fun updateTrending(periodTime: String, movies: List<MovieResult>) {
        deleteTrending(periodTime)
        movies.forEach {
            insert(it)
        }
    }

}