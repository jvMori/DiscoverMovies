package com.example.jvmori.discovermovies.util

import androidx.room.TypeConverter
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson



class Converters {
    companion object {
        @JvmStatic
        var gson = Gson()

        @TypeConverter
        @JvmStatic
        fun stringToMovie(data: String?): List<MovieResult> {
           return genericToList(data)
        }

        @TypeConverter
        @JvmStatic
        fun movieToString(movie: List<MovieResult>): String {
            return gson.toJson(movie)
        }

        @TypeConverter
        @JvmStatic
        fun stringToList(data: String?): List<Int> {
           return genericToList<Int>(data)
        }

        @TypeConverter
        @JvmStatic
        fun listToString(movie: List<Int>): String {
            return gson.toJson(movie)
        }

        fun <T> genericToList(data: String?): List<T> {
            if (data == null) {
                mutableListOf<T>()
            }

            val listType = object : TypeToken<List<T>>() {

            }.type
            return gson.fromJson(data, listType)
        }
    }

}