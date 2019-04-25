package com.example.jvmori.discovermovies.data.local
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Observable

@Dao
interface SavedMovieDao {

    @Insert
    fun insert(movie: MovieResult)

    @Delete
    fun delete(movie: MovieResult)

    @Query("Select * from saved_movies")
    fun getAllSaved() : Observable<List<MovieResult>>
}