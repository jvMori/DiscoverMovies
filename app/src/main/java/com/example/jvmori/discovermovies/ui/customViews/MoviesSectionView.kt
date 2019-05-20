package com.example.jvmori.discovermovies.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.jvmori.discovermovies.R
import kotlinx.android.synthetic.main.movie_section.view.*


class MoviesSectionView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.movie_section, this)
        val titleTextView : TextView = findViewById(R.id.titleTextView)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MoviesSectionView,
            0,
            0
        ).apply {
            try{
                titleTextView.text = getString(R.styleable.MoviesSectionView_titleText)
            }finally {
                recycle()
            }
        }
    }

    fun setTitleText(title : String){
        titleTextView.text = title
    }

}