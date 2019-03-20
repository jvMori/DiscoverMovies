package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.network.response.credits.Cast
import com.example.jvmori.discovermovies.util.Const
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.cast_item.view.*

class CastAdapter(
    private val casts: List<Cast> = mutableListOf()
) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false)
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int = casts.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bindView(casts[position])
    }

    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(cast: Cast){
            itemView.title.text = cast.name
            LoadImage.loadImage(itemView.context, itemView.icon, Const.base_poster_url + cast.profilePath)
        }
    }
}