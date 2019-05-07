package com.example.jvmori.discovermovies.util

import android.widget.TextView

class SetupGenres {
    companion object {
        fun setup(genreIds : List<Int>, categoryItem : TextView, genres : Map<Int, String>, lastIndex : Int){
            genreIds.forEachIndexed { index, item ->
                categoryItem.append(genres[item])
                if (index != lastIndex)
                    categoryItem.append(" | ")
            }
        }
    }
}