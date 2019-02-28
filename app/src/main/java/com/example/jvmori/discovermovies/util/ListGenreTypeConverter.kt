package com.example.jvmori.discovermovies.util

import androidx.room.TypeConverter
import com.example.jvmori.discovermovies.data.network.response.Genre
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.util.*


class ListGenreTypeConverter
{
    companion object {
        var gson = Gson()

        @TypeConverter
        @JvmStatic
        fun stringToSomeObjectList(data: String?): List<Genre> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<List<Genre>>() {

            }.type

            return gson.fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun someObjectListToString(someObjects: List<Genre>): String {
            return gson.toJson(someObjects)
        }
    }
}