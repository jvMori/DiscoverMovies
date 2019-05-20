package com.example.jvmori.discovermovies.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.jvmori.discovermovies.R
import android.content.res.TypedArray



class MoviesSectionView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private lateinit var mTitle : TextView
    init {
        val inflater : LayoutInflater = context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.movie_section, this)

        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.MoviesSectionView, 0, 0
        )
        
        a.recycle()
    }

    fun setTitleText(title : String){
        mTitle.text = title
    }

}