package com.example.jvmori.discovermovies.util

import android.widget.ImageView
import com.example.jvmori.discovermovies.R
import com.squareup.picasso.Picasso

class LoadImage {
    companion object {
        fun loadImage(view: ImageView, imageUrl: String) {
            Picasso.get()
                .load(Const.base_poster_url + imageUrl)
                .error(R.drawable.gradient)
                .placeholder(R.drawable.gradient)
                .into(view)
        }
    }
}