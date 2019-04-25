package com.example.jvmori.discovermovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import com.example.jvmori.discovermovies.data.local.entity.MovieResult

@Dao
interface SavedMovieDao {
    @Insert
    fun insert(movie: MovieResult)
}