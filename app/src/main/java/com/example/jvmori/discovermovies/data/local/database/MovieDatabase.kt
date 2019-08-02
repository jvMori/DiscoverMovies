package com.example.jvmori.discovermovies.data.local.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jvmori.discovermovies.data.local.GenreDao
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.local.SavedMovieDao
import com.example.jvmori.discovermovies.data.local.entity.Collection
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.util.Converters

@Database(entities = [Genre::class, DiscoverMovieResponse::class, MovieResult::class], version = 10, exportSchema = false)
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
            instance ?: buildDatabase(context, this.instance?.savedMovieDao() ).also { instance = it }
        }

        private fun prePopulate(savedMovieDao: SavedMovieDao?) : RoomDatabase.Callback{
            return object : RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    savedMovieDao?.insertCollection(Collection.LIKES.toString())
                    savedMovieDao?.insertCollection(Collection.WATCHED.toString())
                    savedMovieDao?.insertCollection(Collection.TO_WATCH.toString())
                }
            }
        }


        private fun buildDatabase(context: Context, savedMovieDao: SavedMovieDao?) =
            Room.databaseBuilder(
                context,
                MovieDatabase::class.java, "movies.db"
            )
                .addCallback(prePopulate(savedMovieDao))
                .fallbackToDestructiveMigration()
                .build()

    }
}