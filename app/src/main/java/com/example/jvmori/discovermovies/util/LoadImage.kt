package com.example.jvmori.discovermovies.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.example.jvmori.discovermovies.R

class LoadImage {
    companion object {
        fun loadImage(context: Context, view: ImageView, imageUrl: String) {
            Glide.
                with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.gradient)
                .error(R.drawable.gradient)
                .into(view)
        }
    }
}