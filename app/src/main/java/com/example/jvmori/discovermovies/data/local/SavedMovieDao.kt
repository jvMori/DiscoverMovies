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

    @Query("Select * from saved_movies where category like:categoryName AND period like:periodTime")
    fun getAllTrending(periodTime: String, categoryName : String): Single<List<MovieResult>>

    @Query("Delete from saved_movies where category like:categoryName AND period like:periodTime")
    fun deleteTrending(periodTime: String, categoryName : String)

    @Transaction
    fun updateMovies(periodTime: String, categoryName : String, movies: List<MovieResult>) {
        deleteTrending(periodTime, categoryName)
        movies.forEach {
            insert(it)
        }
    }

}