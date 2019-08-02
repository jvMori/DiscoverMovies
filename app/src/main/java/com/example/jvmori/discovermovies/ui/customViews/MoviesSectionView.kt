package com.example.jvmori.discovermovies.ui.customViews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.BaseAdapter
import com.example.jvmori.discovermovies.ui.adapters.SimilarMoviesAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.movie_section.view.*


class MoviesSectionView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    lateinit var adapter : SimilarMoviesAdapter

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
                iconIv.setImageDrawable(getDrawable(R.styleable.MoviesSectionView_icon))
            }finally {
                recycle()
            }
        }
    }

    fun setTitleText(title : String){
        titleTextView.text = title
    }

    fun setIcon(drawable: Drawable){
        iconIv.setImageDrawable(drawable)
    }

    fun setRecyclerView(context: Context, movies: List<MovieResult>){
        rvMovies.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rvMovies.setHasFixedSize(true)
        adapter = SimilarMoviesAdapter()
        adapter.setItems(movies)
        rvMovies.adapter = adapter
    }

    fun setIOnItemClickedListener(iOnItemClickListener: BaseAdapter.IOnItemClickListener<MovieResult>){
        adapter.iOnItemClickListener = iOnItemClickListener
    }
}