package com.example.jvmori.discovermovies.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jvmori.discovermovies.data.local.GenreDao
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.local.SavedMovieDao
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.util.Converters

@Database(entities = [Genre::class, DiscoverMovieResponse::class, MovieResult::class], version = 9, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun moviesDao(): MovieDao
    abstract fun savedMovieDao() : SavedMovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                MovieDatabase::class.java, "movies.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}