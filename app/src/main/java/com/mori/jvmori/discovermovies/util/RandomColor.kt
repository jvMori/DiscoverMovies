package com.mori.jvmori.discovermovies.util

import android.graphics.Color

class RandomColor
{
    companion object {
        val colors = mutableListOf(
             "#01295F",
            "#7CB518", "#00A5CF","#d63ed3",
            "#5603AD")

        private var currentIndex = -1
        fun generateColor() : Int {
            if (currentIndex < colors.size - 1)
                ++currentIndex
            else
                currentIndex = 0
            return Color.parseColor(colors[currentIndex])
        }
    }
}