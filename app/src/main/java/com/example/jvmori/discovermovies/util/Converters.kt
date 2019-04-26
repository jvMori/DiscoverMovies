package com.example.jvmori.discovermovies.util

import android.graphics.Movie
import androidx.room.TypeConverter
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson



class Converters {
    companion object {
        @JvmStatic
        var gson = Gson()
        var gsonForGenres = Gson()

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

        @TypeConverter
        @JvmStatic
        fun stringToList(data: String?): List<Int> {
            if (data == null) {
                mutableListOf<Int>()
            }

            val listType = object : TypeToken<List<Int>>() {

            }.type
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun listToString(movie: List<Int>): String {
            return gsonForGenres.toJson(movie)
        }

        fun <T> genericToList(data: String?, gson: Gson): List<T> {
            if (data == null) {
                mutableListOf<T>()
            }

            val listType = object : TypeToken<List<T>>() {

            }.type
            return gson.fromJson(data, listType)
        }
    }
}