package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.ui.view.discover.DiscoverFragmentDirections
import com.example.jvmori.discovermovies.util.RandomColor
import kotlinx.android.synthetic.main.genre_item.view.*

class GenreAdapter(
    val fragment : Fragment
) : BaseAdapter<Genre>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        return GenreViewHolder(view, fragment)
    }

    class GenreViewHolder(itemView: View, fragment: Fragment) : BaseViewHolder<Genre>(itemView){
        private val _fragment = fragment
        override fun bindView(item: Genre) {
            itemView.itemName.text = item.name
            itemView.cardView2.setCardBackgroundColor(RandomColor.generateColor())
            itemView.setOnClickListener {
                navigateToMovieList(item, fragment = _fragment )
            }
        }
        private fun navigateToMovieList(item: Genre, fragment: Fragment){
            val id = item.idGenre
            val action = DiscoverFragmentDirections.specifyGenreId().setGenre(id)
            val nav =  NavHostFragment.findNavController(fragment)
            nav.navigate(action)
        }
    }
}