package com.example.jvmori.discovermovies.util

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.time.ZonedDateTime


class ZonedTimeTypeConverter
{
    companion object {
        var gson = Gson()

        @TypeConverter
        @JvmStatic
        fun stringToTime(data: String?): ZonedDateTime {
            val listType = object : TypeToken<ZonedDateTime>() {
            }.type
            return gson.fromJson(data, listType)
        }
        @TypeConverter
        @JvmStatic
        fun timeToString(movie: ZonedDateTime): String {
            return gson.toJson(movie)
        }
    }

}