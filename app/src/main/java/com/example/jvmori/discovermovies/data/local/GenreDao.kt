package com.example.jvmori.discovermovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jvmori.discovermovies.data.local.entity.GenreEntry
import io.reactivex.Observable

@Dao
interface GenreDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genres: GenreEntry)

    @Query("select * from movie_table")
    fun getAllGenres() : Observable<GenreEntry>
}