package com.example.jvmori.discovermovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jvmori.discovermovies.data.local.entity.Genre
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface GenreDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genres: List<Genre>)

    @Query("select * from movie_table where idGenre like :genreId")
    fun getGenre(genreId: Int) : Single<Genre>

    @Query("select * from movie_table order by name")
    fun getAllGenres() : Maybe<List<Genre>>
}