package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.IOnClickListener
import com.example.jvmori.discovermovies.util.Const
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private var onClickListener: IOnClickListener?
) : PagedListAdapter<MovieResult, MoviesAdapter.MovieViewHolder>(MovieDiffCallback) {

    private var genres : Map<Int, String> = mutableMapOf<Int, String>()
    private lateinit var onFavIconClickListener: OnFavIconClickListener

    fun setOnFavClickListener(onFavIconClickListener: OnFavIconClickListener){
        this.onFavIconClickListener = onFavIconClickListener
    }

    companion object {
        val MovieDiffCallback = object : DiffUtil.ItemCallback<MovieResult>() {
            override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
                return oldItem == newItem
            }
        }

        fun setStars(rating: Double, starsLayout: LinearLayout) {
            val maxNumberOfStars = starsLayout.childCount
            val ratingFromPercentage = rating * maxNumberOfStars / 100
            val starsCount = Math.floor(ratingFromPercentage)
            val halfStar = Math.round(ratingFromPercentage).toDouble()

            for (i in 0 until starsLayout.childCount) {
                val imageView = starsLayout.getChildAt(i) as ImageView
                if (i < starsCount) {
                    imageView.setImageResource(R.drawable.ic_star)
                }
                if (halfStar != starsCount && i.toDouble() == halfStar - 1) {
                    imageView.setImageResource(R.drawable.ic_star_half)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bindView(it)
            holder.bindGenres(it,genres)
            holder.itemView.setOnClickListener { onClickListener?.onMovieItemClicked(currentItem.id) }
            holder.setOnFavClickListener(onFavIconClickListener, it)
        }
    }

     class MovieViewHolder(itemView: View) : BaseAdapter.BaseViewHolder<MovieResult>(itemView) {
         override fun bindView(item: MovieResult) {
            this.itemView.titleItem.text = item.title
            this.itemView.yearItem.text = item.releaseDate
            this.itemView.ratingItem.text = item.voteAverage.toString()
            this.itemView.reviewItem.text = item.voteCount.toString()
            this.itemView.iconItem.clipToOutline = true
            this.itemView.categoryItem.text = ""
            LoadImage.loadImage(this.itemView.context, this.itemView.iconItem, Const.base_poster_url + item.posterPath)
            MoviesAdapter.setStars(item.voteAverage * 10, this.itemView.layoutStars)
        }

         fun setOnFavClickListener(onFavIconClickListener: OnFavIconClickListener?, item: MovieResult){
             this.itemView.heart.setOnClickListener {
                 onFavIconClickListener?.onFavClicked(item)
             }
         }

         fun bindGenres(movieResult: MovieResult, genres: Map<Int, String>){
             movieResult.genreIds.forEachIndexed { index, item ->
                 itemView.categoryItem.append(genres[item])
                 if (index != movieResult.genreIds.lastIndex)
                     itemView.categoryItem.append(" | ")
             }
         }
    }

    fun setGenres(genres : Map<Int, String>){
        this.genres = genres
    }

    public interface OnFavIconClickListener{
        fun onFavClicked(movieResult: MovieResult)
    }
}