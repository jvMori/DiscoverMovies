package com.example.jvmori.discovermovies.data.local

enum class Collection{
    LIKES,
    TO_WATCH,
    WATCHED,
    NONE;

    companion object {
        fun getName (collType : Collection) : String {
            return when (collType){
                LIKES -> "Favorites"
                TO_WATCH -> "To Watch"
                WATCHED -> "Watched"
                NONE -> "None"
            }
        }
    }
}