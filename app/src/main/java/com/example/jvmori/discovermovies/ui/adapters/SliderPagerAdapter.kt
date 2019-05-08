package com.example.jvmori.discovermovies.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.util.Const
import com.example.jvmori.discovermovies.util.LoadImage
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
        val movieItem = movies[position]

        slideLayout.slideTitle.text = movieItem.title
        slideLayout.review.text = movieItem.voteAverage.toString()
        LoadImage.loadImage(context, slideLayout.posterSlide, Const.base_backdrop_url + movieItem.backdropPath)
        SetupGenres.setup(movieItem.genreIds, slideLayout.slideGenre, genres, movies[position].genreIds.size -1)

        container.addView(slideLayout)
        return slideLayout
    }
    override fun getCount(): Int {
        return movies.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}