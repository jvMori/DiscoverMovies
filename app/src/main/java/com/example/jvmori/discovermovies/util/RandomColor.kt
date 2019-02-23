package com.example.jvmori.discovermovies.util

import android.graphics.Color
import java.util.*

class RandomColor
{
    companion object {
        val colors = mutableListOf(
            "#e5ab3a", "#01295F",
            "#7CB518",  "#00A5CF",
            "#5603AD")

        fun generateColor() : Int {
            val randomColor = Random()
            val index = randomColor.nextInt(colors.size)
            return Color.parseColor(colors[index])
        }
    }
}