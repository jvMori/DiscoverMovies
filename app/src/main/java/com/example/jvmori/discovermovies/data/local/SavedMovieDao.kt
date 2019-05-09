package com.example.jvmori.discovermovies.data.local
import android.graphics.Movie
import androidx.room.*
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface SavedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movie: List<MovieResult>)

    @Delete
    fun delete(movie: MovieResult)

    @Query("Select * from saved_movies")
    fun getAllSaved() : Observable<List<MovieResult>>

    @Query("Select * from saved_movies where id like :movieId")
    fun getMovie(movieId: Int) : Single<MovieResult>

    @Query("Select * from saved_movies where isTrending AND period like:periodTime")
    fun getAllTrending(periodTime : String) : Maybe<List<MovieResult>>
}