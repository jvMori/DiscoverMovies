package com.example.jvmori.discovermovies.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.jvmori.discovermovies.R
import android.content.res.TypedArray



class MoviesSectionView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private lateinit var mTitleTextView : TextView
    private var mTitle : String?
    init {
        val inflater : LayoutInflater = context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.movie_section, this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MoviesSectionView,
            0,
            0
        ).apply {
            try{
                mTitle = getString(R.styleable.MoviesSectionView_titleText)
            }finally {
                recycle()
            }
        }
    }

    fun setTitleText(title : String){
        mTitleTextView.text = title
    }

}