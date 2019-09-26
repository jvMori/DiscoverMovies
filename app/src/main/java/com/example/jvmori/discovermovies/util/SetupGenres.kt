package com.example.jvmori.discovermovies.util

import android.widget.TextView

class SetupGenres {
    companion object {
        fun setup(genreIds : List<Int>?, categoryItem : TextView?, genres : Map<Int, String>, lastIndex : Int){
            categoryItem?.text = ""
            genreIds?.forEachIndexed { index, item ->
                if (genres.isNotEmpty()){
                    categoryItem?.append(genres[item])
                    if (index != lastIndex)
                        categoryItem?.append(" | ")
                }
            }
        }
    }
}