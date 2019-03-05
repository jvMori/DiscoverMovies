package com.example.jvmori.discovermovies.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.ui.view.discover.DiscoverFragmentDirections
import com.example.jvmori.discovermovies.util.RandomColor
import kotlinx.android.synthetic.main.genre_item.view.*

class GenreAdapter(
    private val allGenres: List<Genre> = mutableListOf(),
    private val fragment : Fragment
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = allGenres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.itemView.itemName.text = allGenres[position].name
        holder.itemView.cardView2.setCardBackgroundColor(RandomColor.generateColor())
        holder.itemView.setOnClickListener {
           navigateToMovieList(position)
        }
    }

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private fun navigateToMovieList(position: Int){
        val id = allGenres[position].idGenre
        val action = DiscoverFragmentDirections.specifyGenreId().setGenre(id)
        val nav =  NavHostFragment.findNavController(fragment)
        nav.navigate(action)
    }
}