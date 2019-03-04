package com.example.jvmori.discovermovies.util

import androidx.room.TypeConverter
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson



class MoviesListTypeConverter {
    companion object {
        @JvmStatic
        var gson = Gson()

        @TypeConverter
        @JvmStatic
        fun stringToMovie(data: String?): List<MovieResult> {
            if (data == null) {
               mutableListOf<MovieResult>()
            }

            val listType = object : TypeToken<List<MovieResult>>() {

            }.type
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun movieToString(movie: List<MovieResult>): String {
            return gson.toJson(movie)
        }
    }

}