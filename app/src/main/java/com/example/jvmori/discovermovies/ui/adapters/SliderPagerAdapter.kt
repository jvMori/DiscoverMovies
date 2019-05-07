package com.example.jvmori.discovermovies.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.util.SetupGenres
import kotlinx.android.synthetic.main.slide_item.view.*

class SliderPagerAdapter(
    private var context : Context,
    private var movies : List<MovieResult> = mutableListOf(),
    private var genres : Map<Int, String> = mutableMapOf()
) : PagerAdapter(){
    
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
       return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val slideLayout = inflater.inflate(R.layout.slide_item, null)

        slideLayout.slideTitle.text = movies[position].title
        SetupGenres.setup(movies[position].genreIds, slideLayout.slideGenre, genres, 1)

        container.addView(slideLayout)
        return slideLayout
    }
    override fun getCount(): Int {
        return movies.size
    }
}